package ru.easycode.zerotoheroandroidtdd.core

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel

class App : Application(), ProvideViewModel {
    private lateinit var factory: ViewModelFactory

    private val clear: ClearViewModel = object : ClearViewModel {
        override fun clear(viewModelClass: Class<out ViewModel>) =
            factory.clear(viewModelClass)
    }

    override fun onCreate() {
        super.onCreate()
        factory = ViewModelFactory.Base(ProvideViewModel.Base(clear))
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T =
        factory.viewModel(viewModelClass)
}
