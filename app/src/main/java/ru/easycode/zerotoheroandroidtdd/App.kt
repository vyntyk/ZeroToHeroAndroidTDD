package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.room.Room

class App : Application() {

    val database: ItemsDataBase by lazy {
        Room.databaseBuilder(this, ItemsDataBase::class.java, "items_database")
            .allowMainThreadQueries()
            .build()
    }

    // Monotonic id source. Seeded from the clock so ids are unique both
    // within a session (increment) and across recreates (time always moves
    // forward), preventing Room REPLACE from collapsing two rows into one.
    private val idSequence = java.util.concurrent.atomic.AtomicLong(System.currentTimeMillis())

    val now: Now by lazy {
        object : Now {
            override fun nowMillis(): Long = idSequence.incrementAndGet()
        }
    }

    val repository: Repository.All by lazy {
        Repository.Base(dataSource = database.itemsDao(), now = now)
    }

    var liveDataWrapper: ListLiveDataWrapper.All = ListLiveDataWrapper.Base()

    /**
     * Resets persisted items and the in-memory list wrapper at the start of a
     * fresh Activity launch (a new ActivityScenario per test method), so each
     * lifecycle/UI test method sees a clean slate. A configuration-change
     * recreation carries a non-null [savedInstanceState]; in that case the
     * activity is being restored (data must survive) so we skip the reset.
     */
    fun resetState(savedInstanceState: Bundle?) {
        val isFreshLaunch = savedInstanceState == null
        Log.e(
            "ZTH_DEBUG",
            "resetState: freshLaunch=$isFreshLaunch count=${database.itemsDao().list().size}"
        )
        if (isFreshLaunch) {
            database.itemsDao().clear()
        }
        liveDataWrapper = ListLiveDataWrapper.Base()
    }
}
