package ru.easycode.zerotoheroandroidtdd.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.BundleWrapper
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
    fun save(bundleWrapper: BundleWrapper.Save) {
        liveDataWrapper.save(bundleWrapper)
    }

    fun restore(bundleWrapper: BundleWrapper.Restore) {
        val list = bundleWrapper.restore()
        liveDataWrapper.update(list)
    }

}