package zlagoda.ukma.edu.ua.screens.products_search

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
import zlagoda.ukma.edu.ua.screens.products_search.ui.ProductSearchList
import zlagoda.ukma.edu.ua.screens.products_search.viewmodel.ProductsSearchEvent
import zlagoda.ukma.edu.ua.screens.products_search.viewmodel.ProductsSearchState
import zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel.CustomerCardsSearchViewModel
import zlagoda.ukma.edu.ua.screens.products_search.viewmodel.ProductsSearchViewModel

@Composable
internal fun ProductSearchScreen(
    onCloseClick: () -> Unit
) {
    StoredViewModel(factory = { ProductsSearchViewModel() }) { viewModel ->
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
                is ProductsSearchState.ProductList -> {
                    ProductSearchList(
                        state = state,
                        onSearch = { productName, category ->
                            viewModel.obtainEvent(ProductsSearchEvent.SearchProducts(productName, category))
                        }
                    )
                }
                is ProductsSearchState.Loading -> CircularProgressIndicator()
            }
        }
    }
}
