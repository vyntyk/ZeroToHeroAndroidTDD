package ru.easycode.zerotoheroandroidtdd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemsDao {

    @Query("SELECT * FROM item_cache")
    fun list(): List<ItemCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: ItemCache)
}
