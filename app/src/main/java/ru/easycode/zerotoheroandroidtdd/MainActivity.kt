package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as App).viewModel
        val adapter = Adapter()
        binding.recyclerView.adapter = adapter

        binding.actionButton.setOnClickListener{
            val text = binding.inputEditText.text.toString()
            viewModel.add(text)
            binding.inputEditText.text?.clear()
        }
        viewModel.liveData().observe(this){
            adapter.update(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.save(BundleWrapper.Base(outState))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModel.restore(BundleWrapper.Base(savedInstanceState))
    }
}