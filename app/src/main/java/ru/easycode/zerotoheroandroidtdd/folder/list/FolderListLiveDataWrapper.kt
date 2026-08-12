package ru.easycode.zerotoheroandroidtdd.folder.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface FolderListLiveDataWrapper {

    fun getLiveData(): LiveData<List<FolderUi>>

    interface UpdateListAndRead {
        fun update(list: List<FolderUi>)
    }

    interface Create {
        fun create(folderUi: FolderUi)
    }

    class Base : FolderListLiveDataWrapper, UpdateListAndRead, Create {

        private val liveData = MutableLiveData<List<FolderUi>>()

        override fun getLiveData(): LiveData<List<FolderUi>> = liveData

        override fun update(list: List<FolderUi>) {
            liveData.value = list
        }

        override fun create(folderUi: FolderUi) {
            val current = liveData.value?.toMutableList() ?: mutableListOf()
            current.add(folderUi)
            liveData.value = current
        }
    }
}
