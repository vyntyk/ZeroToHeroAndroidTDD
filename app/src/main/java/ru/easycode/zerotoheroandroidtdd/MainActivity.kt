package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.coroutines.runBlocking
import ru.easycode.zerotoheroandroidtdd.core.AppDataBase
import ru.easycode.zerotoheroandroidtdd.core.FolderCache
import ru.easycode.zerotoheroandroidtdd.core.NoteCache
import ru.easycode.zerotoheroandroidtdd.databinding.FolderDetailsBinding
import ru.easycode.zerotoheroandroidtdd.databinding.CreateFolderBinding
import ru.easycode.zerotoheroandroidtdd.databinding.CreateNoteBinding
import ru.easycode.zerotoheroandroidtdd.databinding.EditFolderBinding
import ru.easycode.zerotoheroandroidtdd.databinding.EditNoteBinding
import ru.easycode.zerotoheroandroidtdd.databinding.FoldersListBinding
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.details.NoteUi
import ru.easycode.zerotoheroandroidtdd.folder.details.NotesAdapter
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderUi
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersAdapter
import java.util.concurrent.atomic.AtomicLong

class MainActivity : AppCompatActivity() {

    private val db by lazy {
        Room.inMemoryDatabaseBuilder(
            applicationContext,
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
    }

    private val foldersDao by lazy { db.foldersDao() }
    private val notesDao by lazy { db.notesDao() }

    private val now = object : ru.easycode.zerotoheroandroidtdd.note.core.Now {
        private val counter = AtomicLong(System.currentTimeMillis())
        override fun timeInMillis(): Long = counter.incrementAndGet()
    }

    private val folderLiveDataWrapper = FolderLiveDataWrapper.Base(0L)

    private val backStack = ArrayDeque<String>()
    private var lastNoteId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToFoldersList(push = true)
    }

    override fun onBackPressed() {
        if (backStack.size > 1) {
            backStack.removeLast()
            when (backStack.last()) {
                "folders" -> goToFoldersList(push = false)
                "folder_details" -> goToFolderDetails(push = false)
                "create_folder" -> goToCreateFolder(push = false)
                "create_note" -> goToCreateNote(push = false)
                "edit_folder" -> goToEditFolder(folderLiveDataWrapper.folderId(), push = false)
                "edit_note" -> goToEditNote(lastNoteId, push = false)
            }
        } else {
            finish()
        }
    }

    private fun goTo(screen: String, push: Boolean, render: () -> Unit) {
        if (push) backStack.addLast(screen)
        render()
    }

    private fun goToFoldersList(push: Boolean) {
        goTo("folders", push) {
            val binding = FoldersListBinding.inflate(layoutInflater)
            val data = runBlocking {
                foldersDao.folders().map { f ->
                    FolderUi(id = f.id, title = f.text, notesCount = notesDao.notes(f.id).size)
                }
            }
            val adapter = FoldersAdapter { folderUi ->
                folderLiveDataWrapper.update(folderUi)
                goToFolderDetails(push = true)
            }
            adapter.update(data)
            binding.foldersRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.foldersRecyclerView.adapter = adapter
            binding.addButton.setOnClickListener { goToCreateFolder(push = true) }
            setContentView(binding.root)
        }
    }

    private fun goToFolderDetails(push: Boolean) {
        goTo("folder_details", push) {
            val binding = FolderDetailsBinding.inflate(layoutInflater)
            val folder = folderLiveDataWrapper.liveDataValue().value
            if (folder != null) {
                val freshName = runBlocking { foldersDao.folders().find { it.id == folder.id }?.text } ?: folder.title
                binding.folderNameTextView.text = freshName
                val count = runBlocking { notesDao.notes(folder.id).size }
                binding.notesCountTextView.text = count.toString()
            }
            val data = runBlocking {
                notesDao.notes(folderLiveDataWrapper.folderId()).map { n ->
                    NoteUi(id = n.id, title = n.text, folderId = n.folderId)
                }
            }
            val adapter = NotesAdapter { noteUi ->
                lastNoteId = noteUi.id
                goToEditNote(noteUi.id, push = true)
            }
            adapter.update(data)
            binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.notesRecyclerView.adapter = adapter
            binding.addNoteButton.setOnClickListener {
                android.util.Log.d("TDDNAV", "addNoteButton clicked")
                goToCreateNote(push = true)
            }
            binding.editFolderButton.setOnClickListener {
                goToEditFolder(folderLiveDataWrapper.folderId(), push = true)
            }
            setContentView(binding.root)
        }
    }

    private fun goToCreateFolder(push: Boolean) {
        goTo("create_folder", push) {
            val binding = CreateFolderBinding.inflate(layoutInflater)
            binding.saveFolderButton.setOnClickListener {
                val name = binding.createFolderEditText.text.toString()
                if (name.isNotEmpty()) {
                    runBlocking {
                        val id = now.timeInMillis()
                        foldersDao.insert(FolderCache(id = id, text = name))
                    }
                    onBackPressed()
                }
            }
            setContentView(binding.root)
        }
    }

    private fun goToCreateNote(push: Boolean) {
        android.util.Log.d("TDDNAV", "goToCreateNote called")
        goTo("create_note", push) {
            val binding = CreateNoteBinding.inflate(layoutInflater)
            android.util.Log.d("TDDNAV", "create_note binding root id=${binding.root.id}")
            binding.saveNoteButton.setOnClickListener {
                val text = binding.createNoteEditText.text.toString()
                if (text.isNotEmpty()) {
                    val folderId = folderLiveDataWrapper.liveDataValue().value?.id ?: 0L
                    runBlocking {
                        val id = now.timeInMillis()
                        notesDao.insert(NoteCache(id = id, folderId = folderId, text = text))
                    }
                    folderLiveDataWrapper.increment()
                    onBackPressed()
                }
            }
            setContentView(binding.root)
            android.util.Log.d("TDDNAV", "create_note setContentView done")
            window.decorView.post {
                val stillThere =
                    window.decorView.findViewById<View>(R.id.folderNameTextView) != null
                android.util.Log.d("TDDNAV", "after layout folderNameTextView still present=$stillThere")
            }
        }
    }

    private fun goToEditFolder(folderId: Long, push: Boolean) {
        goTo("edit_folder", push) {
            val binding = EditFolderBinding.inflate(layoutInflater)
            runBlocking {
                val folder = foldersDao.folders().find { it.id == folderId }
                folder?.let { binding.folderEditText.setText(it.text) }
            }
            binding.saveFolderButton.setOnClickListener {
                val newName = binding.folderEditText.text.toString()
                runBlocking { foldersDao.insert(FolderCache(id = folderId, text = newName)) }
                onBackPressed()
            }
            binding.deleteFolderButton.setOnClickListener {
                runBlocking {
                    notesDao.deleteByFolderId(folderId)
                    foldersDao.delete(folderId)
                }
                backStack.clear()
                backStack.add("folders")
                goToFoldersList(push = false)
            }
            setContentView(binding.root)
        }
    }

    private fun goToEditNote(noteId: Long, push: Boolean) {
        goTo("edit_note", push) {
            val binding = EditNoteBinding.inflate(layoutInflater)
            runBlocking {
                val note = notesDao.note(noteId)
                binding.noteEditText.setText(note.text)
            }
            binding.saveNoteButton.setOnClickListener {
                val newText = binding.noteEditText.text.toString()
                runBlocking {
                    val note = notesDao.note(noteId)
                    notesDao.insert(note.copy(text = newText))
                }
                onBackPressed()
            }
            binding.deleteNoteButton.setOnClickListener {
                runBlocking { notesDao.delete(noteId) }
                onBackPressed()
            }
            setContentView(binding.root)
        }
    }
}
