package ru.easycode.zerotoheroandroidtdd.note.core

import ru.easycode.zerotoheroandroidtdd.core.NoteCache
import ru.easycode.zerotoheroandroidtdd.core.NotesDao

interface NotesRepository {

    suspend fun createNote(folderId: Long, text: String): Long

    suspend fun noteList(folderId: Long): List<MyNote>

    suspend fun note(noteId: Long): MyNote

    suspend fun deleteNote(noteId: Long)

    suspend fun renameNote(noteId: Long, newText: String)

    interface Create {
        suspend fun createNote(folderId: Long, text: String): Long
    }

    interface ReadList {
        suspend fun noteList(folderId: Long): List<MyNote>
    }

    interface Edit {
        suspend fun note(noteId: Long): MyNote
        suspend fun deleteNote(noteId: Long)
        suspend fun renameNote(noteId: Long, newName: String)
    }

    class Base(
        private val now: Now,
        private val dao: NotesDao
    ) : NotesRepository, Create, ReadList, Edit {

        override suspend fun createNote(folderId: Long, text: String): Long {
            val id = now.timeInMillis()
            dao.insert(NoteCache(id = id, folderId = folderId, text = text))
            return id
        }

        override suspend fun noteList(folderId: Long): List<MyNote> {
            return dao.notes(folderId).map { cache ->
                MyNote(
                    id = cache.id,
                    title = cache.text,
                    folderId = cache.folderId
                )
            }
        }

        override suspend fun note(noteId: Long): MyNote {
            val cache = dao.note(noteId)
            return MyNote(
                id = cache.id,
                title = cache.text,
                folderId = cache.folderId
            )
        }

        override suspend fun deleteNote(noteId: Long) {
            dao.delete(noteId)
        }

        override suspend fun renameNote(noteId: Long, newText: String) {
            val existing = dao.note(noteId)
            dao.insert(existing.copy(text = newText))
        }
    }
}
