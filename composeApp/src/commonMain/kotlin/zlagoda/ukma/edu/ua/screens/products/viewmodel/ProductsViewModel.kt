package zlagoda.ukma.edu.ua.screens.products.viewmodel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.di.Injection

class ProductsViewModel(
    private val productsRepository: ProductRepository = Injection.productRepository,
    private val categoriesRepository: CategoryRepository = Injection.categoryRepository
): ViewModel<ProductsState, ProductsAction, ProductsEvent>(
    initialState = ProductsState.Loading,
) {
    init {
        getProducts()
        getCategoryNames()
    }

    override fun obtainEvent(viewEvent: ProductsEvent) {
        when (viewEvent) {
            is ProductsEvent.SetProductList -> processChangeProductList(viewEvent.productList)
            is ProductsEvent.SaveProduct -> processSaveProduct(viewEvent.product)
            is ProductsEvent.DeleteProduct -> processDeleteProduct(viewEvent.product)
            is ProductsEvent.EditProduct -> processEditProduct(viewEvent.product)
            is ProductsEvent.CreateNewProduct -> processNewProduct()
        }
    }

    private fun getProducts() {
        productsRepository.getAllProducts()
            .onEach { products ->
                processChangeProductList(products)
            }.launchIn(viewModelScope)
    }

    private fun getCategoryNames() {
        categoriesRepository.getAllCategories()
            .onEach { categories ->
                processChangeCategoryNames(categories)
            }.launchIn(viewModelScope)
    }

    private fun processChangeProductList(productList: List<Product>) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is ProductsState.ProductList) {
                setViewState(ProductsState.ProductList(productList, currentState.categoryNumberToName))
            } else {
                setViewState(ProductsState.ProductList(productList, emptyMap()))
            }
        }
    }

    private fun processChangeCategoryNames(categoryList: List<Category>) {
        withViewModelScope {
            val currentState = viewStates().value
            val categoryIdToName = categoryList.associate { it.id to it.name }
            if (currentState is ProductsState.ProductList) {
                setViewState(ProductsState.ProductList(currentState.products, categoryIdToName))
            } else {
                setViewState(ProductsState.ProductList(emptyList(), categoryIdToName))
            }
        }
    }

    private fun processSaveProduct(product: Product) {
        withViewModelScope {
            productsRepository.insertProduct(product)
        }
    }

    private fun processDeleteProduct(product: Product) {
        withViewModelScope {
            productsRepository.deleteProductByIdProduct(product.idProduct)
        }
    }

    private fun processEditProduct(product: Product) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is ProductsState.ProductList) {
                setViewAction(ProductsAction.OpenEditProductDialog(product, currentState.categoryNumberToName))
            }
        }
    }

    private fun processNewProduct() {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is ProductsState.ProductList) {
                setViewAction(ProductsAction.OpenNewProductDialog(currentState.categoryNumberToName))
            }
        }
    }
}