package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ListLiveDataWrapper {

    interface Add {
        fun add(value: ItemUi)
    }

    interface All : Add {
        fun update(value: List<ItemUi>)
        fun liveData(): LiveData<List<ItemUi>>
        fun delete(item: ItemUi)
    }

    class Base : All {

        private val _liveData = MutableLiveData<List<ItemUi>>(emptyList())

        override fun update(value: List<ItemUi>) {
            _liveData.value = value
        }

        override fun liveData(): LiveData<List<ItemUi>> = _liveData

        override fun add(value: ItemUi) {
            val current = _liveData.value.orEmpty().toMutableList()
            current.add(value)
            _liveData.value = current
        }

        override fun delete(item: ItemUi) {
            val current = _liveData.value.orEmpty().toMutableList()
            current.remove(item)
            _liveData.value = current
        }
    }
}
