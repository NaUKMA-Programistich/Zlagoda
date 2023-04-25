package zlagoda.ukma.edu.ua.screens.store_products.viewmodel

import zlagoda.ukma.edu.ua.db.StoreProduct

sealed class StoreProductsState {

    data class StoreProductList(val storeProducts: List<StoreProduct>, val idProductToName: Map<Long, String>) : StoreProductsState()

    object Loading : StoreProductsState()
}