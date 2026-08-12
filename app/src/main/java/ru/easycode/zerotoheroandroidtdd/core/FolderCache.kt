package ru.easycode.zerotoheroandroidtdd.core

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_cache")
data class FolderCache(
    @PrimaryKey
    val id: Long,
    val text: String
)
