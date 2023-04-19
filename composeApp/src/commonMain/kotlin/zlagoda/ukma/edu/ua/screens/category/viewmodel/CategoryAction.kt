package zlagoda.ukma.edu.ua.screens.category.viewmodel

import zlagoda.ukma.edu.ua.db.Category


sealed class CategoryAction {
    data class OpenEditCategoryDialog(val category: Category) : CategoryAction()
    object OpenNewCategoryDialog : CategoryAction()
}