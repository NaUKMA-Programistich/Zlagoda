package zlagoda.ukma.edu.ua.screens.products_search.viewmodel

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.di.Injection

class ProductsSearchViewModel(
    private val productsRepository: ProductRepository = Injection.productRepository,
    private val categoriesRepository: CategoryRepository = Injection.categoryRepository
): ViewModel<ProductsSearchState, ProductsSearchAction, ProductsSearchEvent>(
    initialState = ProductsSearchState.Loading,
) {
    init {
        viewModelScope.launch {
            productsRepository.getAllSortProducts().collectLatest { products ->
                categoriesRepository.getAllCategories().collectLatest { categories ->
                    setViewState(ProductsSearchState.ProductList(products, categories))
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: ProductsSearchEvent) {
        when (viewEvent) {
            is ProductsSearchEvent.SearchProducts -> processSearch(viewEvent.productName, viewEvent.category)
        }
    }

    private fun processSearch(productName: String, category: Category?) {
        withViewModelScope {
            setViewState(ProductsSearchState.Loading)
//            productsRepository.searchByProductNameAndCategory(productName, category?.name).collectLatest { products ->
//                categoriesRepository.getAllCategories().collectLatest { categories ->
//                    setViewState(ProductsSearchState.ProductList(products, categories))
//                }
//            }
        }
    }
}