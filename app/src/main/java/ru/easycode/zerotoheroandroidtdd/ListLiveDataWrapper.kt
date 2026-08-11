package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData

interface ListLiveDataWrapper {

    interface Mutable : Read {

        fun update(value: List<String>)
    }

    interface Read {

        fun liveData(): LiveData<List<String>>
    }

    interface Add {

        fun add(value: String)
    }

    class Base : Mutable {

        private val liveData = androidx.lifecycle.MutableLiveData<List<String>>()

        override fun update(value: List<String>) {
            liveData.value = value
        }

        override fun liveData(): LiveData<List<String>> {
            return liveData
        }
    }
}
