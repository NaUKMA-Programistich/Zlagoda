package zlagoda.ukma.edu.ua.screens.category.viewmodel

import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeAction


sealed class CategoryAction {
    data class OpenEditCategoryDialog(val category: Category) : CategoryAction()
    object OpenNewCategoryDialog : CategoryAction()

}