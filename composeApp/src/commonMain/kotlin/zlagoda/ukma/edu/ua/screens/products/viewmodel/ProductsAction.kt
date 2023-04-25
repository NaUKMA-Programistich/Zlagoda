package zlagoda.ukma.edu.ua.screens.products.viewmodel

import zlagoda.ukma.edu.ua.db.Product

sealed class ProductsAction {

    data class OpenEditProductDialog(val product:  Product,val categoryNumberToName: Map<Long, String>) : ProductsAction()

    data class OpenNewProductDialog(val categoryNumberToName: Map<Long, String>) : ProductsAction()
    object OpenSearchDialog : ProductsAction()
}