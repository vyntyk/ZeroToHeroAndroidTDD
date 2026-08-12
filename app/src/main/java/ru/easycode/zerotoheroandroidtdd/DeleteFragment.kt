package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers

class DeleteFragment : Fragment() {

    companion object {
        private const val ARG_ITEM_ID = "item_id"

        fun newInstance(itemId: Long) = DeleteFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_ITEM_ID, itemId)
            }
        }
    }

    private lateinit var viewModel: DeleteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemTitleTextView = view.findViewById<TextView>(R.id.itemTitleTextView)
        val deleteButton = view.findViewById<Button>(R.id.deleteButton)

        val itemId = arguments?.getLong(ARG_ITEM_ID) ?: 0L

        val app = requireActivity().application as App
        viewModel = DeleteViewModel(
            deleteLiveDataWrapper = app.liveDataWrapper,
            repository = app.repository,
            clear = FragmentClearViewModel(parentFragmentManager),
            dispatcher = Dispatchers.IO,
            dispatcherMain = Dispatchers.Main
        )

        viewModel.liveData.observe(viewLifecycleOwner) { text ->
            itemTitleTextView.text = text
        }

        viewModel.init(itemId)

        deleteButton.setOnClickListener {
            viewModel.delete(itemId)
        }
    }
}
