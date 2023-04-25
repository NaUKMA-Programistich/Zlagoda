package zlagoda.ukma.edu.ua.screens.store_products.viewmodel

import zlagoda.ukma.edu.ua.db.StoreProduct

sealed class StoreProductsEvent {

    data class SetStoreProductList(val storeProductList: List<StoreProduct>) : StoreProductsEvent()

    data class DeleteStoreProduct(val storeProduct: StoreProduct) : StoreProductsEvent()

    data class SaveStoreProduct(val storeProduct: StoreProduct) : StoreProductsEvent()

    data class EditStoreProduct(val storeProduct: StoreProduct) : StoreProductsEvent()

    object CreateNewStoreProduct: StoreProductsEvent()
    object SearchStoreProduct: StoreProductsEvent()
}