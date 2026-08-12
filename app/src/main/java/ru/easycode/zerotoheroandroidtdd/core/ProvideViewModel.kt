package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(clasz: Class<T>): T

    class Factory(private val provide: ProvideViewModel) : ProvideViewModel {

        private val cache = mutableMapOf<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            var vm = cache[clasz] as? T
            if (vm == null) {
                vm = provide.viewModel(clasz)
                cache[clasz] = vm
            }
            return vm
        }

        fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            for (clasz in viewModelClasses) {
                cache.remove(clasz)
            }
        }
    }
}
