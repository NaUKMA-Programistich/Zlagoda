package zlagoda.ukma.edu.ua.screens.store_product.viewmodel

import zlagoda.ukma.edu.ua.db.StoreProduct

sealed class StoreProductsAction {

    data class OpenEditStoreProductDialog(val storeProduct:  StoreProduct, val idProductToName: Map<Long, String>) : StoreProductsAction()

    data class OpenNewStoreProductDialog(val idProductToName: Map<Long, String>) : StoreProductsAction()
    object OpenSearchDialog : StoreProductsAction()
}