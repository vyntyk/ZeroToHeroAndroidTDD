package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var decrementButton: Button
    private lateinit var incrementButton: Button

    private lateinit var uiState: UiState
    private val count = Count.Base(2, 4, 0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        decrementButton = findViewById(R.id.decrementButton)
        incrementButton = findViewById(R.id.incrementButton)
        textView = findViewById(R.id.countTextView)

        incrementButton.setOnClickListener {
            uiState = count.increment(textView.text.toString())
            uiState.apply(textView, decrementButton, incrementButton)
        }

        decrementButton.setOnClickListener {
            uiState = count.decrement(textView.text.toString())
            uiState.apply(textView, decrementButton, incrementButton)
            // incrementButton.isEnabled = true

        }
        if (savedInstanceState == null) {
            uiState = count.initial(textView.text.toString())
            uiState.apply(textView, decrementButton, incrementButton)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("key", uiState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        uiState = savedInstanceState.getSerializable("key") as UiState
        uiState.apply(textView, decrementButton, incrementButton)
    }
}