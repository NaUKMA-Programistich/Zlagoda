package zlagoda.ukma.edu.ua.screens.category.viewmodel

import zlagoda.ukma.edu.ua.db.Category

sealed class CategoryEvent {
    data class SetCategoryList(val categoryList: List<Category>) : CategoryEvent()
    data class DeleteCategory(val category: Category) : CategoryEvent()
    data class SaveCategory(val category: Category) : CategoryEvent()
    data class EditCategory(val category: Category) : CategoryEvent()
    object CreateNewCategory: CategoryEvent()

}
