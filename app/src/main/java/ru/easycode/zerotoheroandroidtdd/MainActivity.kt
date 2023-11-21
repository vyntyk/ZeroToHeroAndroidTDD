package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var inputEditText: TextInputEditText
    private lateinit var actionButton: Button
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.inputEditText)
        actionButton = findViewById(R.id.actionButton)
        titleTextView = findViewById(R.id.titleTextView)

        actionButton.isEnabled = false

        actionButton.setOnClickListener {
            titleTextView.text = inputEditText.text
            inputEditText.setText("")
        }

        inputEditText.addTextChangedListener {
            actionButton.isEnabled = inputEditText.text.toString().length >= 3
        }
    }
}