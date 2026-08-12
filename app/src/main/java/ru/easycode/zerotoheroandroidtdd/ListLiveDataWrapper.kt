package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ListLiveDataWrapper {

    interface Add {
        fun add(value: ItemUi)
    }

    interface All : Add {
        fun update(list: List<ItemUi>)
        fun update(item: ItemUi)
        fun liveData(): LiveData<List<ItemUi>>
        fun delete(item: ItemUi)
    }

    class Base : All {

        private val _liveData = MutableLiveData<List<ItemUi>>(emptyList())

        override fun update(list: List<ItemUi>) {
            _liveData.value = list
        }

        override fun update(item: ItemUi) {
            val current = _liveData.value.orEmpty().toMutableList()
            val index = current.indexOfFirst { it.areItemsSame(item) }
            if (index >= 0) {
                current[index] = item
            }
            _liveData.value = current
        }

        override fun liveData(): LiveData<List<ItemUi>> = _liveData

        override fun add(value: ItemUi) {
            val current = _liveData.value.orEmpty().toMutableList()
            current.add(value)
            _liveData.value = current
        }

        override fun delete(item: ItemUi) {
            val current = _liveData.value.orEmpty().toMutableList()
            val index = current.indexOfFirst { it.areItemsSame(item) }
            if (index >= 0) {
                current.removeAt(index)
            }
            _liveData.value = current
        }
    }
}
