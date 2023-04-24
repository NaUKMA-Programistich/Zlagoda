package zlagoda.ukma.edu.ua.screens.products.viewmodel

import zlagoda.ukma.edu.ua.db.Product

sealed class ProductsState {

    data class ProductList(val products: List<Product>, val categoryNumberToName: Map<Long, String>) : ProductsState()

    object Loading : ProductsState()
}