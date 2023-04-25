package zlagoda.ukma.edu.ua.screens.products_search.viewmodel

import zlagoda.ukma.edu.ua.db.Category

sealed class ProductsSearchEvent {

    data class SearchProducts(val productName: String, val category: Category?) : ProductsSearchEvent()

}