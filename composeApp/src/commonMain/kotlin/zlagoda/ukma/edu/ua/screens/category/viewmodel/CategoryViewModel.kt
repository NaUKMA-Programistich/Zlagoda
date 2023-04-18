package zlagoda.ukma.edu.ua.screens.category.viewmodel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.di.Singleton

class CategoryViewModel(
    private val repository: CategoryRepository = Singleton.categoryRepository
): ViewModel<CategoryState, CategoryAction, CategoryEvent>(
    initialState = CategoryState.CategoryList(categories = emptyList()),
) {


    init {
        getCategories()
    }

    override fun obtainEvent(viewEvent: CategoryEvent) {
        when (viewEvent) {
            is CategoryEvent.SetCategoryList -> processChangeCategoryList(viewEvent.categoryList)
            is CategoryEvent.SelectCategoryItem -> processSelectCategoryItem(viewEvent.category)
            is CategoryEvent.SetCategoryName -> processSetCategoryName(viewEvent.name)
            is CategoryEvent.SaveCategory -> processSaveCategory(viewEvent.id, viewEvent.name)
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
            if (currentState is CategoryState.CategoryList) {
                setViewState(CategoryState.CategoryItem(
                    id = category.id,
                    name = category.name
                ))
            }
        }
    }

    private fun processSetCategoryName(name: String) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is CategoryState.CategoryItem) {
                setViewState(CategoryState.CategoryItem(
                    id = currentState.id,
                    name = name
                ))
            }
        }
    }

    private fun processSaveCategory(
        id: Long?,
        name: String
    ) {
        withViewModelScope {
            val currentState = viewStates().value
            repository.insertCategory(id, name)
            if (currentState is CategoryState.CategoryItem) {
                getCategories()
            }
        }
    }

    private fun processAddNewCategory() {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is CategoryState.CategoryList) {
                setViewState(CategoryState.CategoryItem(
                    id = null,
                    name = ""
                ))
            }
        }
    }

    private fun processDeleteCategory(category: Category) {
        withViewModelScope {
            val currentState = viewStates().value
            repository.deleteCategoryById(category.id)
            if (currentState is CategoryState.CategoryList) {
                getCategories()
            }
        }
    }

}