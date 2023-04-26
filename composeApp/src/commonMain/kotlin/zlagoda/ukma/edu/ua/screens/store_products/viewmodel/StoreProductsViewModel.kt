package zlagoda.ukma.edu.ua.screens.store_products.viewmodel

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.data.store_product.StoreProductRepository
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.db.StoreProduct
import zlagoda.ukma.edu.ua.di.Injection

class StoreProductsViewModel(
    private val storeProductsRepository: StoreProductRepository = Injection.storeProductRepository,
    private val productsRepository: ProductRepository = Injection.productRepository
): ViewModel<StoreProductsState, StoreProductsAction, StoreProductsEvent>(
    initialState = StoreProductsState.Loading,
) {

    private var job: Job? = null

    init {
        getStoreProducts()
        getProductNames()
    }

    override fun obtainEvent(viewEvent: StoreProductsEvent) {
        when (viewEvent) {
            is StoreProductsEvent.SetStoreProductList -> processChangeStoreProductList(viewEvent.storeProductList)
            is StoreProductsEvent.SaveStoreProduct -> processSaveStoreProduct(viewEvent.storeProduct)
            is StoreProductsEvent.DeleteStoreProduct -> processDeleteStoreProduct(viewEvent.storeProduct)
            is StoreProductsEvent.EditStoreProduct -> processEditStoreProduct(viewEvent.storeProduct)
            is StoreProductsEvent.CreateNewStoreProduct -> processNewStoreProduct()
            is StoreProductsEvent.SearchStoreProduct -> processSearch()
            is StoreProductsEvent.ChangeFilterSortType -> getStoreProducts(viewEvent.all, viewEvent.prom, viewEvent.byName)
            StoreProductsEvent.GetStoreProductDetails -> processGetStoreProductDetails()
        }
    }

    private fun processSearch() {
        withViewModelScope {
            setViewAction(StoreProductsAction.OpenSearchDialog)
        }
    }
    private fun getStoreProducts(all: Boolean = true, prom: Boolean = true, byName: Boolean = true) {
        job?.cancel() // cancel prev job if we have one
        val storeProductsFlow = if (all) {
            storeProductsRepository.getStoreProductsSortedByProductsNumber()
        } else {
            if (prom) {
                if (byName) storeProductsRepository.getPromotionalStoreProductsSortedByName()
                else storeProductsRepository.getPromotionalStoreProductsSortedByProductsNumber()
            } else {
                if (byName) storeProductsRepository.getNotPromotionalStoreProductsSortedByName()
                else storeProductsRepository.getNotPromotionalStoreProductsSortedByProductsNumber()
            }
        }
        job = storeProductsFlow.onEach { storeProducts ->
            processChangeStoreProductList(storeProducts)
        }.launchIn(viewModelScope)
    }

    private fun getProductNames() {
        productsRepository.getAllSortProducts()
            .onEach { products ->
                processChangeProductNames(products)
            }.launchIn(viewModelScope)
    }

    private fun processChangeStoreProductList(storeProductList: List<StoreProduct>) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is StoreProductsState.StoreProductList) {
                setViewState(StoreProductsState.StoreProductList(storeProductList, currentState.idProductToName))
            } else {
                setViewState(StoreProductsState.StoreProductList(storeProductList, emptyMap()))
            }
        }
    }

    private fun processChangeProductNames(productList: List<Product>) {
        withViewModelScope {
            val currentState = viewStates().value
            val idProductToName = productList.associate { it.idProduct to it.productName }
            if (currentState is StoreProductsState.StoreProductList) {
                setViewState(StoreProductsState.StoreProductList(currentState.storeProducts, idProductToName))
            } else {
                setViewState(StoreProductsState.StoreProductList(emptyList(), idProductToName))
            }
        }
    }

    private fun processSaveStoreProduct(storeProduct: StoreProduct) {
        withViewModelScope {
            storeProductsRepository.insertStoreProduct(storeProduct)
        }
    }

    private fun processDeleteStoreProduct(storeProduct: StoreProduct) {
        withViewModelScope {
            storeProductsRepository.deleteStoreProductByUPC(storeProduct.upc)
        }
    }

    private fun processEditStoreProduct(storeProduct: StoreProduct) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is StoreProductsState.StoreProductList) {
                setViewAction(StoreProductsAction.OpenEditStoreProductDialog(storeProduct, currentState.idProductToName))
            }
        }
    }

    private fun processNewStoreProduct() {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is StoreProductsState.StoreProductList) {
                setViewAction(StoreProductsAction.OpenNewStoreProductDialog(currentState.idProductToName))
            }
        }
    }

    private fun processGetStoreProductDetails() {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is StoreProductsState.StoreProductList) {
                setViewAction(StoreProductsAction.OpenProductDetailsDialog)
            }
        }
    }
}