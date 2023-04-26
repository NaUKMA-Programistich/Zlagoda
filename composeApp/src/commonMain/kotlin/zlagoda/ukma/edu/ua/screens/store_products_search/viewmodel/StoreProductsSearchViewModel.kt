package zlagoda.ukma.edu.ua.screens.store_products_search.viewmodel

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.data.store_product.StoreProductRepository
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.di.Injection

class StoreProductsSearchViewModel(
    private val storeProductsRepository: StoreProductRepository = Injection.storeProductRepository,
    private val productsRepository: ProductRepository = Injection.productRepository,
    private val categoriesRepository: CategoryRepository = Injection.categoryRepository
): ViewModel<StoreProductsSearchState, StoreProductsSearchAction, StoreProductsSearchEvent>(
    initialState = StoreProductsSearchState.Loading,
) {
    init {
        viewModelScope.launch { processNotFilter() }
    }

    private suspend fun processNotFilter() {
        storeProductsRepository.getAllStoreProducts().collectLatest  { storeProducts ->
            productsRepository.getAllSortProducts().collectLatest { products ->
                categoriesRepository.getAllCategories().collectLatest { categories ->
                    setViewState(StoreProductsSearchState.StoreProductList(storeProducts, products, categories))
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: StoreProductsSearchEvent) {
        println("ProductsSearchViewModel: obtainEvent $viewEvent")
        when (viewEvent) {
            is StoreProductsSearchEvent.SearchStoreProducts -> processSearch(viewEvent.productName, viewEvent.category, viewEvent.upc)
        }
    }

    private fun processSearch(productName: String, category: Category?, upc: String) {
        withViewModelScope {
            val state = viewStates().value
            if (state !is StoreProductsSearchState.StoreProductList) return@withViewModelScope
            val categories = state.categories
            val products = state.products

            if (productName.isBlank() && upc.isBlank() && category == null) {
                processNotFilter()
                return@withViewModelScope
            }

            if (productName.isNotBlank()) {
                val filter = storeProductsRepository.searchByProductName(productName)
                setViewState(StoreProductsSearchState.StoreProductList(filter, products, categories))
                return@withViewModelScope
            }

            if (upc.isNotBlank()) {
                val res = storeProductsRepository.getStoreProductByUPC(upc)
                val filter = if (res == null) emptyList() else listOf(res)
                setViewState(StoreProductsSearchState.StoreProductList(filter, products, categories))
                return@withViewModelScope
            }

            if (category != null) {
                val filter = storeProductsRepository.searchByCategoryName(category.name)
                setViewState(StoreProductsSearchState.StoreProductList(filter, products, categories))
                return@withViewModelScope
            }
        }
    }
}