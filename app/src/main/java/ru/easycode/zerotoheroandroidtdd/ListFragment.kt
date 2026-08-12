package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers

class ListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val addButton = view.findViewById<Button>(R.id.addButton)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val app = requireActivity().application as App
        viewModel = MainViewModel(
            repository = app.repository,
            liveDataWrapper = app.liveDataWrapper,
            dispatcher = Dispatchers.IO,
            dispatcherMain = Dispatchers.Main
        )

        viewModel.liveData().observe(viewLifecycleOwner) { items ->
            recyclerView.adapter = ItemAdapter(items) { item ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, DetailsFragment.newInstance(item.id))
                    .addToBackStack(null)
                    .commit()
            }
        }

        addButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.init()
    }

    override fun onResume() {
        super.onResume()
        if (::viewModel.isInitialized) {
            viewModel.init()
        }
    }
}
