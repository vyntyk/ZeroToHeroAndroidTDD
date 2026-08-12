package ru.easycode.zerotoheroandroidtdd.folder.core

import ru.easycode.zerotoheroandroidtdd.core.FolderCache
import ru.easycode.zerotoheroandroidtdd.core.FoldersDao
import ru.easycode.zerotoheroandroidtdd.core.NotesDao
import ru.easycode.zerotoheroandroidtdd.note.core.Now

interface FoldersRepository {

    suspend fun createFolder(name: String): Long

    suspend fun folders(): List<Folder>

    suspend fun delete(folderId: Long)

    suspend fun rename(folderId: Long, newName: String)

    interface Create {
        suspend fun createFolder(name: String): Long
    }

    interface ReadList {
        suspend fun folders(): List<Folder>
    }

    interface Edit {
        suspend fun delete(folderId: Long)
        suspend fun rename(folderId: Long, newName: String)
    }

    class Base(
        private val now: Now,
        private val foldersDao: FoldersDao,
        private val notesDao: NotesDao
    ) : FoldersRepository, Create, ReadList, Edit {

        override suspend fun createFolder(name: String): Long {
            val id = now.timeInMillis()
            foldersDao.insert(FolderCache(id = id, text = name))
            return id
        }

        override suspend fun folders(): List<Folder> {
            val foldersCache = foldersDao.folders()
            return foldersCache.map { folderCache ->
                val notesCount = notesDao.notes(folderCache.id).size
                Folder(
                    id = folderCache.id,
                    title = folderCache.text,
                    notesCount = notesCount
                )
            }
        }

        override suspend fun delete(folderId: Long) {
            notesDao.deleteByFolderId(folderId)
            foldersDao.delete(folderId)
        }

        override suspend fun rename(folderId: Long, newName: String) {
            foldersDao.insert(FolderCache(id = folderId, text = newName))
        }
    }
}
