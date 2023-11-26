package ru.easycode.zerotoheroandroidtdd.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.create.CreateScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation

class ListViewModel (
    private val liveDataWrapper: ListLiveDataWrapper.Mutable,
    private val navigation: Navigation.Update
): ListLiveDataWrapper.Read, ViewModel() {
    fun create() {
        navigation.update(CreateScreen)
    }

    override fun liveData() = liveDataWrapper.liveData()

}