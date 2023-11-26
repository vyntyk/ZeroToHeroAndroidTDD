package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.list.ListScreen

class MainViewModel(
    private val navigation: Navigation.Mutable) : ViewModel(), Navigation.Read {
    fun init(firstRun: Boolean) {
        if (firstRun)
            navigation.update(ListScreen)
    }
    override fun liveData() =  navigation.liveData()
}