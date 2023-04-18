package zlagoda.ukma.edu.ua.screens.category.viewmodel

import zlagoda.ukma.edu.ua.db.Category

sealed class CategoryEvent {
    data class SetCategoryList(val categoryList: List<Category>) : CategoryEvent()
    data class SelectCategoryItem(val category: Category) : CategoryEvent()
    object AddNewCategory : CategoryEvent()
    data class DeleteCategory(val category: Category) : CategoryEvent()
    data class SaveCategory(val name: String) : CategoryEvent()

    data class SetCategoryName(val name: String) : CategoryEvent()

    object BackToCategoryList : CategoryEvent()
}
