package zlagoda.ukma.edu.ua.screens.category.viewmodel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.di.Injection

class CategoryViewModel(
    private val repository: CategoryRepository = Injection.categoryRepository
): ViewModel<CategoryState, CategoryAction, CategoryEvent>(
    initialState = CategoryState.Loading,
) {
    init { getCategories() }

    override fun obtainEvent(viewEvent: CategoryEvent) {
        when (viewEvent) {
            is CategoryEvent.SetCategoryList -> processChangeCategoryList(viewEvent.categoryList)
            is CategoryEvent.SaveCategory -> processSaveCategory(viewEvent.category)
            is CategoryEvent.DeleteCategory -> processDeleteCategory(viewEvent.category)
            is CategoryEvent.EditCategory -> processEditCategory(viewEvent.category)
            is CategoryEvent.CreateNewCategory -> processNewCategory()
        }
    }

    private fun getCategories() {
        repository.getAllCategories()
            .onEach { categories ->
                processChangeCategoryList(categories)
            }.launchIn(viewModelScope)
    }

    private fun processChangeCategoryList(categoryList: List<Category>) {
        withViewModelScope {
            setViewState(CategoryState.CategoryList(categoryList))
        }
    }

    private fun processSaveCategory(category: Category) {
        withViewModelScope {
            repository.insertCategory(category)
        }
    }

    private fun processDeleteCategory(category: Category) {
        withViewModelScope {
            repository.deleteCategoryById(category.id)
        }
    }

    private fun processEditCategory(category: Category) {
        withViewModelScope {
            setViewAction(CategoryAction.OpenEditCategoryDialog(category))
        }
    }

    private fun processNewCategory() {
        withViewModelScope {
            setViewAction(CategoryAction.OpenNewCategoryDialog)
        }
    }
}