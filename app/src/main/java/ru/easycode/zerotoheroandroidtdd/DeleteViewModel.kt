package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeleteViewModel(
    private val deleteLiveDataWrapper: ListLiveDataWrapper.All,
    private val repository: Repository.Delete,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    fun init(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            val item = repository.item(itemId)
            withContext(dispatcherMain) {
                _liveData.value = item.text
            }
        }
    }

    fun delete(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            repository.delete(itemId)
            withContext(dispatcherMain) {
                val text = _liveData.value ?: ""
                deleteLiveDataWrapper.delete(ItemUi(id = itemId, text = text))
                clear.clearViewModel(this@DeleteViewModel::class.java)
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(this::class.java)
    }
}
