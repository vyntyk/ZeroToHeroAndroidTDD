package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

interface UiState {
    fun apply(button: Button, progressBar: ProgressBar, textView: TextView)

}
