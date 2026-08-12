package ru.easycode.zerotoheroandroidtdd.core

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteCache)

    @Query("DELETE FROM note_cache WHERE id = :noteId")
    suspend fun delete(noteId: Long)

    @Query("DELETE FROM note_cache WHERE folderId = :folderId")
    suspend fun deleteByFolderId(folderId: Long)

    @Query("SELECT * FROM note_cache WHERE folderId = :folderId ORDER BY id ASC")
    suspend fun notes(folderId: Long): List<NoteCache>

    @Query("SELECT * FROM note_cache WHERE id = :noteId")
    suspend fun note(noteId: Long): NoteCache
}
