package ru.easycode.zerotoheroandroidtdd

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val os: String,
    val ram: Int
)

data class ProductFilter(
    val id: Int,
    val category: String,
    val name: String
)