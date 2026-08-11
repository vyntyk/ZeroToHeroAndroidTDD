package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository: Repository.Mutable

    private val liveDataWrapper = ListLiveDataWrapper.Base()

    init {
        val database = ItemsDataBase.getDatabase(application)
        repository = Repository.Base(
            dataSource = database.itemsDao(),
            now = object : Now {
                override fun nowMillis(): Long = System.currentTimeMillis()
            }
        )
    }

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.list()
            launch(Dispatchers.Main) {
                liveDataWrapper.update(list)
            }
        }
    }

    fun add(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(value = value)
            val list = repository.list()
            launch(Dispatchers.Main) {
                liveDataWrapper.update(list)
            }
        }
    }

    fun liveData(): LiveData<List<String>> {
        return liveDataWrapper.liveData()
    }
}
