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
            is CategoryEvent.SelectCategoryItem -> processSelectCategoryItem(viewEvent.category)
            is CategoryEvent.SetCategoryName -> processSetCategoryName(viewEvent.name)
            is CategoryEvent.SaveCategory -> processSaveCategory(viewEvent.name)
            is CategoryEvent.BackToCategoryList -> getCategories()
            is CategoryEvent.AddNewCategory -> processAddNewCategory()
            is CategoryEvent.DeleteCategory -> processDeleteCategory(viewEvent.category)
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

    private fun processSelectCategoryItem(category: Category) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState !is CategoryState.CategoryList) return@withViewModelScope

            setViewState(CategoryState.CategoryItem(
                id = category.id,
                name = category.name
            ))
        }
    }

    private fun processSetCategoryName(name: String) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState !is CategoryState.CategoryItem) return@withViewModelScope

            setViewState(CategoryState.CategoryItem(
                id = currentState.id,
                name = name
            ))
        }
    }

    private fun processSaveCategory(name: String) {
        withViewModelScope {
            repository.insertCategory(
                Category(id = 0, name = name)
            )
        }
    }

    private fun processAddNewCategory() {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState !is CategoryState.CategoryList) return@withViewModelScope

            setViewState(CategoryState.CategoryItem(
                id = null,
                name = ""
            ))
        }
    }

    private fun processDeleteCategory(category: Category) {
        withViewModelScope {
            repository.deleteCategoryById(category.id)
        }
    }
}