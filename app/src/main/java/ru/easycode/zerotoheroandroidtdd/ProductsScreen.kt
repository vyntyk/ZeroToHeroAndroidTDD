package ru.easycode.zerotoheroandroidtdd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun ProductsScreen(viewModel: ProductsViewModel) {
    val products by viewModel.productsUiListStateFlow.collectAsState()
    val orders by viewModel.ordersUiListStateFlow.collectAsState()
    val filters by viewModel.filtersUiListStateFlow.collectAsState()

    var screen by remember { mutableStateOf(Screen.Products) }

    when (screen) {
        Screen.Products -> ProductsList(
            products = products,
            onOrderClick = { screen = Screen.Order },
            onFilterClick = { screen = Screen.Filters },
        )
        Screen.Order -> OrderSettings(
            orders = orders,
            onChoose = { name ->
                viewModel.chooseOrder(name)
                screen = Screen.Products
            },
        )
        Screen.Filters -> FilterSettings(
            filters = filters,
            onFilterClick = { filter ->
                if (filter.chosen) {
                    viewModel.unchooseFilter(filter.id)
                } else {
                    viewModel.chooseFilter(filter.id)
                }
            },
            onSave = { screen = Screen.Products },
        )
    }
}

private enum class Screen {
    Products, Order, Filters
}

@Composable
private fun ProductsList(
    products: List<ProductListUi>,
    onOrderClick: () -> Unit,
    onFilterClick: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Row {
            Button(
                onClick = onOrderClick,
                modifier = Modifier.testTag("order button"),
            ) {
                Text("order")
            }
            Button(
                onClick = onFilterClick,
                modifier = Modifier.testTag("filters button"),
            ) {
                Text("filters")
            }
        }
        LazyColumn(Modifier.testTag("ProductsLazyColumn")) {
            if (products.isEmpty() || products.first() is ProductListUi.Empty) {
                item {
                    Text(
                        text = "nothing found",
                        modifier = Modifier
                            .padding(16.dp)
                            .testTag("nothing found"),
                    )
                }
            } else {
                itemsIndexed(products) { index, product ->
                    if (product is ProductListUi.Base) {
                        ProductRow(index, product)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductRow(index: Int, product: ProductListUi.Base) {
    Column(
        Modifier
            .padding(8.dp)
            .testTag("Product at $index"),
    ) {
        Text(
            text = product.name,
            modifier = Modifier.testTag("Product name at $index"),
        )
        Text(
            text = product.os,
            modifier = Modifier.testTag("Product os at $index"),
        )
        Text(
            text = product.ram.toString(),
            modifier = Modifier.testTag("Product ram at $index"),
        )
        Text(
            text = product.price,
            modifier = Modifier.testTag("Product price at $index"),
        )
    }
}

@Composable
private fun OrderSettings(
    orders: List<OrderUi>,
    onChoose: (String) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        orders.forEach { order ->
            Text(
                text = order.name,
                modifier = Modifier
                    .testTag("Order option ${order.name}")
                    .selectable(
                        selected = order.chosen,
                        onClick = { onChoose(order.name) },
                    )
                    .padding(16.dp),
            )
        }
    }
}

@Composable
private fun FilterSettings(
    filters: List<FilterUi>,
    onFilterClick: (FilterUi) -> Unit,
    onSave: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        filters.forEach { filter ->
            Text(
                text = "${filter.category} ${filter.value}",
                modifier = Modifier
                    .testTag("filter ${filter.category} ${filter.value}")
                    .selectable(
                        selected = filter.chosen,
                        onClick = { onFilterClick(filter) },
                    )
                    .padding(16.dp),
            )
        }
        Button(
            onClick = onSave,
            modifier = Modifier.testTag("save button"),
        ) {
            Text("save")
        }
    }
}