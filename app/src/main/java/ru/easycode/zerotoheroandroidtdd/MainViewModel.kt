package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repository: Repository.Read,
    private val liveDataWrapper: ListLiveDataWrapper.All,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun init() {
        viewModelScope.launch(dispatcher) {
            val list = repository.list().map { ItemUi(id = it.id, text = it.text) }
            withContext(dispatcherMain) {
                liveDataWrapper.update(list)
            }
        }
    }

    fun liveData(): LiveData<List<ItemUi>> = liveDataWrapper.liveData()
}
