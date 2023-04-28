package zlagoda.ukma.edu.ua.screens.category.viewmodel

import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Product

sealed class CategoryState {
    data class CategoryList(val categories: List<Category>, val products: List<Product>) : CategoryState()
    object Loading : CategoryState()
    object NotSupport : CategoryState()
}