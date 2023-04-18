package zlagoda.ukma.edu.ua.screens.category.viewmodel

import zlagoda.ukma.edu.ua.db.Category

sealed class CategoryState {
    data class CategoryList(val categories: List<Category>) : CategoryState()
    object Loading : CategoryState()
}