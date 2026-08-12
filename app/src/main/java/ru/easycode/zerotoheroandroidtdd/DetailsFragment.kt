package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers

class DetailsFragment : Fragment() {

    companion object {
        private const val ARG_ITEM_ID = "item_id"

        fun newInstance(itemId: Long) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_ITEM_ID, itemId)
            }
        }
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemTextView = view.findViewById<TextView>(R.id.itemTextView)
        val itemInputEditText = view.findViewById<TextInputEditText>(R.id.itemInputEditText)
        val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        val updateButton = view.findViewById<Button>(R.id.updateButton)

        val itemId = arguments?.getLong(ARG_ITEM_ID) ?: 0L

        val app = requireActivity().application as App
        viewModel = DetailsViewModel(
            changeLiveDataWrapper = app.liveDataWrapper,
            repository = app.repository,
            clear = FragmentClearViewModel(parentFragmentManager),
            dispatcher = Dispatchers.IO,
            dispatcherMain = Dispatchers.Main
        )

        viewModel.liveData.observe(viewLifecycleOwner) { text ->
            itemTextView.text = text
            if (itemInputEditText.text.toString() != text) {
                itemInputEditText.setText(text)
            }
        }

        viewModel.init(itemId)

        deleteButton.setOnClickListener {
            viewModel.delete(itemId)
        }

        updateButton.setOnClickListener {
            val newText = itemInputEditText.text.toString().trim()
            viewModel.update(itemId, newText)
        }
    }
}
