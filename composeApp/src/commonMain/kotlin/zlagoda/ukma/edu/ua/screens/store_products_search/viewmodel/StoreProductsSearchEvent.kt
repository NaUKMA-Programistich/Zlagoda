package zlagoda.ukma.edu.ua.screens.store_products_search.viewmodel

import zlagoda.ukma.edu.ua.db.Category

sealed class StoreProductsSearchEvent {

    data class SearchStoreProducts(
        val productName: String,
        val category: Category? = null,
        val upc: String
    ) : StoreProductsSearchEvent()

}