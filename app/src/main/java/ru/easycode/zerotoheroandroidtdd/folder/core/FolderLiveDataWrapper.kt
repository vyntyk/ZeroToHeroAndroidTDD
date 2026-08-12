package ru.easycode.zerotoheroandroidtdd.folder.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderUi

interface FolderLiveDataWrapper {

    fun folderId(): Long

    interface Mutable {
        fun update(folder: FolderUi)
        fun folderId(): Long
    }

    interface Update {
        fun update(folder: FolderUi)
    }

    interface Increment {
        fun increment()
    }

    interface Decrement {
        fun decrement()
    }

    interface Rename {
        fun rename(newName: String)
    }

    class Base(
        private val initialFolderId: Long
    ) : FolderLiveDataWrapper, Mutable, Increment, Decrement, Rename {

        private val liveData = MutableLiveData<FolderUi>()

        fun liveDataValue(): LiveData<FolderUi> = liveData

        override fun folderId(): Long = liveData.value?.id ?: initialFolderId

        override fun update(folder: FolderUi) {
            liveData.value = folder
        }

        override fun increment() {
            val current = liveData.value
            if (current != null) {
                val newCount = current.notesCount + 1
                liveData.value = current.copy(notesCount = newCount)
            }
        }

        override fun decrement() {
            val current = liveData.value
            if (current != null) {
                val newCount = maxOf(0, current.notesCount - 1)
                liveData.value = current.copy(notesCount = newCount)
            }
        }

        override fun rename(newName: String) {
            val current = liveData.value
            if (current != null) {
                liveData.value = current.copy(title = newName)
            }
        }
    }
}
