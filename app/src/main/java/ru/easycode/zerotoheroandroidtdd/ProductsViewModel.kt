package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class ProductsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ProductsRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    private val productsMutable = MutableStateFlow<List<ProductListUi>>(emptyList())
    val productsUiListStateFlow: StateFlow<List<ProductListUi>> =
        productsMutable.asStateFlow()

    private val ordersMutable = MutableStateFlow<List<OrderUi>>(emptyList())
    val ordersUiListStateFlow: StateFlow<List<OrderUi>> =
        ordersMutable.asStateFlow()

    private val filtersMutable = MutableStateFlow<List<FilterUi>>(emptyList())
    val filtersUiListStateFlow: StateFlow<List<FilterUi>> =
        filtersMutable.asStateFlow()

    private var products: List<Product> = emptyList()
    private var orderList: List<String> = emptyList()
    private var filterList: List<ProductFilter> = emptyList()
    private var chosenOrder: String? = null
    private val chosenFilterIds = mutableSetOf<Int>()

    init {
        val productsFlow: Flow<List<Product>> = flow { emit(repository.products()) }
        val ordersFlow: Flow<List<String>> = flow { emit(repository.orderList()) }
        val filtersFlow: Flow<List<ProductFilter>> = flow { emit(repository.filters()) }

        runAsync.runFlowCollect(viewModelScope, productsFlow) {
            products = it
            updateProducts()
        }
        runAsync.runFlowCollect(viewModelScope, ordersFlow) {
            orderList = it
            if (chosenOrder == null) {
                chosenOrder = it.firstOrNull()
            }
            updateOrders()
        }
        runAsync.runFlowCollect(viewModelScope, filtersFlow) {
            filterList = it
            updateFilters()
        }
    }

    fun chooseOrder(name: String) {
        chosenOrder = name
        updateOrders()
        updateProducts()
    }

    fun chooseFilter(id: Int) {
        val category = filterList.firstOrNull { it.id == id }?.category ?: return
        chosenFilterIds.removeAll(
            chosenFilterIds.filter { filterId ->
                filterList.firstOrNull { filter -> filter.id == filterId }?.category == category
            }
        )
        chosenFilterIds.add(id)
        updateFilters()
        updateProducts()
    }

    fun unchooseFilter(id: Int) {
        chosenFilterIds.remove(id)
        updateFilters()
        updateProducts()
    }

    private fun updateProducts() {
        val order = chosenOrder ?: "alphabet"
        val sorted = when (order) {
            "price: low to high" ->
                products.sortedBy { it.price.filter(Char::isDigit).toInt() }
            "price: high to low" ->
                products.sortedByDescending { it.price.filter(Char::isDigit).toInt() }
            else -> products.sortedBy { it.name }
        }
        val filtered = sorted.filter { product ->
            chosenFilterIds.all { id ->
                val filter = filterList.firstOrNull { it.id == id } ?: return@all true
                when (filter.category) {
                    "os" -> product.os == filter.name
                    "RAM" -> product.ram.toString() == filter.name
                    else -> true
                }
            }
        }
        productsMutable.value = if (filtered.isEmpty()) {
            listOf(ProductListUi.Empty)
        } else {
            filtered.map {
                ProductListUi.Base(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    os = it.os,
                    ram = it.ram
                )
            }
        }
    }

    private fun updateOrders() {
        ordersMutable.value = orderList.map {
            OrderUi(name = it, chosen = it == chosenOrder)
        }
    }

    private fun updateFilters() {
        filtersMutable.value = filterList.map {
            FilterUi(
                id = it.id,
                category = it.category,
                value = it.name,
                chosen = it.id in chosenFilterIds
            )
        }
    }
}