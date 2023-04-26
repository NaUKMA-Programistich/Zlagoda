package zlagoda.ukma.edu.ua.screens.products_search.viewmodel

import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.Product

sealed class ProductsSearchState {

    object Loading : ProductsSearchState()

    data class ProductList(
        val products: List<Product>,
        val categories: List<Category>,
        val currentEmployee: Employee,
    ) : ProductsSearchState()
}