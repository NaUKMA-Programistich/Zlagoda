package zlagoda.ukma.edu.ua.screens.category.viewmodel

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlagoda.ukma.edu.ua.core.ktx.isManager
import zlagoda.ukma.edu.ua.core.ktx.isSeller
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.data.login.LoginRepository
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.di.Injection

class CategoryViewModel(
    private val repository: CategoryRepository = Injection.categoryRepository,
    private val productsRepository: ProductRepository = Injection.productRepository,
    private val loginRepository: LoginRepository = Injection.loginRepository,
): ViewModel<CategoryState, CategoryAction, CategoryEvent>(
    initialState = CategoryState.Loading,
) {
    init {
        withViewModelScope {
            loginRepository.getCurrentEmployee().collectLatest {
                if (it.isManager()) {
                    getCategoriesAndProducts()
                }
                else { setViewState(CategoryState.NotSupport) }
            }
        }
    }

    override fun obtainEvent(viewEvent: CategoryEvent) {
        when (viewEvent) {
            is CategoryEvent.SetCategoryList -> {}
            is CategoryEvent.SaveCategory -> processSaveCategory(viewEvent.category)
            is CategoryEvent.DeleteCategory -> processDeleteCategory(viewEvent.category)
            is CategoryEvent.EditCategory -> processEditCategory(viewEvent.category)
            is CategoryEvent.CreateNewCategory -> processNewCategory()
        }
    }

    private fun getCategoriesAndProducts() {
        withViewModelScope {
            repository.getAllCategories().collectLatest { categories ->
                productsRepository.getAllProducts().collectLatest { products ->
                    setViewState(CategoryState.CategoryList(categories, products))
                }
            }
        }
    }


    private fun processSaveCategory(category: Category) {
        withViewModelScope {
            repository.insertCategory(category)
        }
    }

    private fun processDeleteCategory(category: Category) {
        withViewModelScope {
            val state = viewStates().value
            if(state !is CategoryState.CategoryList) return@withViewModelScope
            if (state.products.any { it.idProduct == category.id }) {
                // No action
            } else {
                repository.deleteCategoryById(category.id)
            }
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