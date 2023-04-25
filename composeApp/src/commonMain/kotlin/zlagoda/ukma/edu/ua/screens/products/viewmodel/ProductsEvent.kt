package zlagoda.ukma.edu.ua.screens.products.viewmodel

import zlagoda.ukma.edu.ua.db.Product

sealed class ProductsEvent {

    data class SetProductList(val productList: List<Product>) : ProductsEvent()

    data class DeleteProduct(val product: Product) : ProductsEvent()

    data class SaveProduct(val product: Product) : ProductsEvent()

    data class EditProduct(val product: Product) : ProductsEvent()

    object CreateNewProduct: ProductsEvent()
    object SearchProduct: ProductsEvent()
}