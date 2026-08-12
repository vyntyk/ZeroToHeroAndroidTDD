package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen

class MainViewModel(
    private val navigation: Navigation.Mutable
) : ViewModel() {

    fun init(firstRun: Boolean = false) {
        if (firstRun) {
            navigation.update(FoldersListScreen)
        }
    }
}
