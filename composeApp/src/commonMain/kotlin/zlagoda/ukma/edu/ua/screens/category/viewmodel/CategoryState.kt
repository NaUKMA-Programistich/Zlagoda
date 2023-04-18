package zlagoda.ukma.edu.ua.screens.category.viewmodel

import zlagoda.ukma.edu.ua.db.Category

sealed class CategoryState {
    data class CategoryList(val categories: List<Category>) : CategoryState()
    data class CategoryItem(val id: Long?, val name: String) : CategoryState()
    object Loading : CategoryState()
}