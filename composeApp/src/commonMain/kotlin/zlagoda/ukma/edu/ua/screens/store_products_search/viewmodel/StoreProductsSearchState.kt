package zlagoda.ukma.edu.ua.screens.store_products_search.viewmodel

import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.db.StoreProduct

sealed class StoreProductsSearchState {

    object Loading : StoreProductsSearchState()

    data class StoreProductList(
        val storeProducts: List<StoreProduct>,
        val products: List<Product>,
        val categories: List<Category>
    ) : StoreProductsSearchState()
}