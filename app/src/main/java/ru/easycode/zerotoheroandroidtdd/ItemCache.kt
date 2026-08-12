package ru.easycode.zerotoheroandroidtdd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_cache")
data class ItemCache(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val text: String
)
