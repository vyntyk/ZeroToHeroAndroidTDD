package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddViewModel(
    private val repository: Repository.Add,
    private val liveDataWrapper: ListLiveDataWrapper.Add,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun add(value: String) {
        viewModelScope.launch(dispatcher) {
            repository.add(value = value)
            withContext(dispatcherMain) {
                liveDataWrapper.add(value = value)
                clear.clearViewModel(this@AddViewModel::class.java)
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(this::class.java)
    }
}
