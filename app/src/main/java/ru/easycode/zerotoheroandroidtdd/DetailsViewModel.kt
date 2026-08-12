package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val changeLiveDataWrapper: ListLiveDataWrapper.All,
    private val repository: Repository.Change,
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
                changeLiveDataWrapper.delete(ItemUi(id = itemId, text = text))
                clear.clearViewModel(this@DetailsViewModel::class.java)
            }
        }
    }

    fun update(itemId: Long, newText: String) {
        viewModelScope.launch(dispatcher) {
            repository.update(id = itemId, newText = newText)
            withContext(dispatcherMain) {
                changeLiveDataWrapper.update(ItemUi(id = itemId, text = newText))
                clear.clearViewModel(this@DetailsViewModel::class.java)
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(this::class.java)
    }
}
