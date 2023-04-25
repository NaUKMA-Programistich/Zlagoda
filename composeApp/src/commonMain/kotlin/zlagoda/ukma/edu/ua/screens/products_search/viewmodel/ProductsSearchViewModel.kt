package zlagoda.ukma.edu.ua.screens.products_search.viewmodel

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.screens.employee_search.viewmodel.EmployeesSearchAction
import zlagoda.ukma.edu.ua.screens.employee_search.viewmodel.EmployeesSearchEvent

class ProductsSearchViewModel(
    private val productsRepository: ProductRepository = Injection.productRepository,
    private val categoriesRepository: CategoryRepository = Injection.categoryRepository
): ViewModel<ProductsSearchState, ProductsSearchAction, ProductsSearchEvent>(
    initialState = ProductsSearchState.Loading,
) {
    init {
        viewModelScope.launch { processNotFilter() }
    }

    private suspend fun processNotFilter() {
        productsRepository.getAllSortProducts().collectLatest { products ->
            categoriesRepository.getAllCategories().collectLatest { categories ->
                setViewState(ProductsSearchState.ProductList(products, categories))
            }
        }
    }

    override fun obtainEvent(viewEvent: ProductsSearchEvent) {
        println("ProductsSearchViewModel: obtainEvent $viewEvent")
        when (viewEvent) {
            is ProductsSearchEvent.SearchProducts -> processSearch(viewEvent.productName, viewEvent.category)
            else -> {}
        }
    }

    private fun processSearch(productName: String, category: Category?) {
        withViewModelScope {
            val state = viewStates().value
            if (state !is ProductsSearchState.ProductList) return@withViewModelScope
            val categories = state.categories

            if (productName.isEmpty() && category == null) {
                processNotFilter()
                return@withViewModelScope
            }

            if (productName.isNotEmpty()) {
                val filter = productsRepository.searchByProductName(productName)
                setViewState(ProductsSearchState.ProductList(filter, categories))
                return@withViewModelScope
            }

            if (category != null) {
                val filter = productsRepository.searchByCategoryName(category.name)
                setViewState(ProductsSearchState.ProductList(filter, categories))
                return@withViewModelScope
            }
        }
    }
}