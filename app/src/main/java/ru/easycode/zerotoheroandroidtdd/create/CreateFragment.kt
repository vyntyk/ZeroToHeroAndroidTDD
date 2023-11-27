package ru.easycode.zerotoheroandroidtdd.create

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import com.google.android.material.internal.ViewUtils.hideKeyboard
import ru.easycode.zerotoheroandroidtdd.core.AbstractFragment
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentCreateBinding

class CreateFragment : AbstractFragment<FragmentCreateBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentCreateBinding.inflate(inflater, container, false)

    private lateinit var viewModel: CreateViewModel

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() = viewModel.comeback()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(CreateViewModel::class.java)

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)


        binding.inputEditText.addTextChangedListener {
            binding.createButton.isEnabled = binding.inputEditText.text.toString().length >= 3
        }

        binding.createButton.setOnClickListener {
            hideKeyboard()
            viewModel.add(binding.inputEditText.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }
}