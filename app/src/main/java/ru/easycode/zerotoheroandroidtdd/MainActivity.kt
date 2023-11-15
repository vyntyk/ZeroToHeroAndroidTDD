package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    private var uiState:UiState = UiState.Base("0")
    private val count = Count.Base(2, 4)
    private lateinit var textView: TextView
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.incrementButton)
        textView = findViewById(R.id.countTextView)

        button.setOnClickListener {
            uiState = count.increment(textView.text.toString())
            uiState.apply(textView, button)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("key", uiState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        uiState = savedInstanceState.getSerializable("key") as UiState
        uiState.apply(textView, button)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}