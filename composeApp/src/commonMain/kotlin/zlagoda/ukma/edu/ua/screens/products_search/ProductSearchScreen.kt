package zlagoda.ukma.edu.ua.screens.products_search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import zlagoda.ukma.edu.ua.core.composable.CategoryDropdown
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.screens.products_search.ui.ProductSearchAppBar
import zlagoda.ukma.edu.ua.screens.products_search.ui.ProductSearchList
import zlagoda.ukma.edu.ua.screens.products_search.viewmodel.ProductsSearchEvent
import zlagoda.ukma.edu.ua.screens.products_search.viewmodel.ProductsSearchState
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
                viewModel.clear()
                onCloseClick()
            }
            when (val state = viewState) {
                is ProductsSearchState.ProductList -> {
                    ProductSearchList(
                        products = state.products,
                        categories = state.categories,
                        onSearch = { productName, category ->
                            viewModel.obtainEvent(ProductsSearchEvent.SearchProducts(productName, category))
                        }
                    )
                }
                is ProductsSearchState.Loading -> CircularProgressIndicator()
            }
        }

        when (val action = viewAction) {
            else -> {}
        }
    }
}
