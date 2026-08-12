package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.room.Room

class App : Application() {

    val database: ItemsDataBase by lazy {
        Room.databaseBuilder(this, ItemsDataBase::class.java, "items_database").build()
    }

    val now: Now by lazy {
        object : Now {
            override fun nowMillis(): Long = System.currentTimeMillis()
        }
    }

    val repository: Repository.All by lazy {
        Repository.Base(dataSource = database.itemsDao(), now = now)
    }

    val liveDataWrapper: ListLiveDataWrapper.All by lazy {
        ListLiveDataWrapper.Base()
    }
}
