package ru.easycode.zerotoheroandroidtdd.note.edit

interface NoteLiveDataWrapper {

    fun update(noteText: String)

    class Base : NoteLiveDataWrapper {

        private var text: String = ""

        override fun update(noteText: String) {
            text = noteText
        }

        fun get(): String = text
    }
}
