package ru.easycode.zerotoheroandroidtdd

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel

interface ClearViewModel {
    fun clearViewModel(clasz: Class<out ViewModel>)
}

class FragmentClearViewModel(
    private val fragmentManager: FragmentManager
) : ClearViewModel {
    override fun clearViewModel(clasz: Class<out ViewModel>) {
        fragmentManager.popBackStack()
    }
}
