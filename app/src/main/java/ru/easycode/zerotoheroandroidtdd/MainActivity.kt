package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var actionButton: Button
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.titleTextView)
        actionButton = findViewById(R.id.actionButton)
        progressBar = findViewById(R.id.progressBar)

        actionButton.setOnClickListener{
            actionButton.isEnabled = false
            progressBar.visibility = View.VISIBLE

            actionButton.postDelayed({
                titleTextView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                actionButton.isEnabled = true
            }, 3500)
        }
    }
}