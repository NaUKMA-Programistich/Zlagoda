package zlagoda.ukma.edu.ua.screens.store_products_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import zlagoda.ukma.edu.ua.screens.products_search.ui.ProductSearchAppBar
import zlagoda.ukma.edu.ua.screens.store_products_search.ui.StoreProductSearchList
import zlagoda.ukma.edu.ua.screens.store_products_search.viewmodel.StoreProductsSearchEvent
import zlagoda.ukma.edu.ua.screens.store_products_search.viewmodel.StoreProductsSearchState
import zlagoda.ukma.edu.ua.screens.store_products_search.viewmodel.StoreProductsSearchViewModel

@Composable
internal fun StoreProductSearchScreen(
    onCloseClick: () -> Unit
) {
    StoredViewModel(factory = { StoreProductsSearchViewModel() }) { viewModel ->
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProductSearchAppBar {
                onCloseClick()
            }
            when (val state = viewState) {
                is StoreProductsSearchState.StoreProductList -> {
                    StoreProductSearchList(
                        state = state,
                        onSearch = { productName, category, productUpc ->
                            viewModel.obtainEvent(StoreProductsSearchEvent.SearchStoreProducts(productName, category, productUpc))
                        }
                    )
                }
                is StoreProductsSearchState.Loading -> CircularProgressIndicator()
            }
        }

        when (val action = viewAction) {
            else -> {}
        }
    }
}
