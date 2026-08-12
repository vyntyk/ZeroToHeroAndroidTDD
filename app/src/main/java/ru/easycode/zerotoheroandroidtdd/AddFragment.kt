package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers

class AddFragment : Fragment() {

    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val input = view.findViewById<TextInputEditText>(R.id.addInputEditText)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        val app = requireActivity().application as App
        viewModel = AddViewModel(
            repository = app.repository,
            liveDataWrapper = app.liveDataWrapper,
            clear = FragmentClearViewModel(parentFragmentManager),
            dispatcher = Dispatchers.IO,
            dispatcherMain = Dispatchers.Main
        )

        saveButton.setOnClickListener {
            val text = input.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.add(text)
            }
        }
    }
}
