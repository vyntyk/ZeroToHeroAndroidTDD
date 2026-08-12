package ru.easycode.zerotoheroandroidtdd.core

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_cache")
data class NoteCache(
    @PrimaryKey
    val id: Long,
    val folderId: Long,
    val text: String
)
