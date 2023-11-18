package ru.easycode.zerotoheroandroidtdd

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is test for Room, no ui expected
 */
@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: ItemsDataBase
    private lateinit var dao: ItemsDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ItemsDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.itemsDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test() {
        assertEquals(emptyList<ItemCache>(), dao.list())

        val cache = ItemCache(id = 1L, text = "first")
        dao.add(item = cache)
        assertEquals(listOf(ItemCache(id = 1L, text = "first")), dao.list())

        val next = ItemCache(id = 2L, text = "second")
        dao.add(item = next)
        assertEquals(
            listOf(
                ItemCache(id = 1L, text = "first"),
                ItemCache(id = 2L, text = "second")
            ), dao.list()
        )
    }
}