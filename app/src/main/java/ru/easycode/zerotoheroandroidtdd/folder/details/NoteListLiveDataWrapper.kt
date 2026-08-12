package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface NoteListLiveDataWrapper {

    fun getLiveData(): LiveData<List<NoteUi>>

    interface Create {
        fun create(noteUi: NoteUi)
    }

    interface Update {
        fun update(noteId: Long, newText: String)
    }

    interface UpdateListAndRead {
        fun update(notes: List<NoteUi>)
    }

    class Base : NoteListLiveDataWrapper, Create, Update, UpdateListAndRead {

        private val liveData = MutableLiveData<List<NoteUi>>()

        override fun getLiveData(): LiveData<List<NoteUi>> = liveData

        override fun create(noteUi: NoteUi) {
            val current = liveData.value?.toMutableList() ?: mutableListOf()
            current.add(noteUi)
            liveData.value = current
        }

        override fun update(noteId: Long, newText: String) {
            val current = liveData.value?.toMutableList() ?: return
            val index = current.indexOfFirst { it.id == noteId }
            if (index >= 0) {
                current[index] = current[index].copy(title = newText)
            }
            liveData.value = current
        }

        override fun update(notes: List<NoteUi>) {
            liveData.value = notes
        }
    }
}
