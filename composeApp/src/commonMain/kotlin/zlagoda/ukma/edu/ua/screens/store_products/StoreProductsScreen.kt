package zlagoda.ukma.edu.ua.screens.store_storeProduct

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.screens.store_products.ui.StoreProductDetailsDialog
import zlagoda.ukma.edu.ua.screens.store_products.ui.StoreProductItemDialog
import zlagoda.ukma.edu.ua.screens.store_products.ui.StoreProductViewList
import zlagoda.ukma.edu.ua.screens.store_products.viewmodel.StoreProductsAction
import zlagoda.ukma.edu.ua.screens.store_products.viewmodel.StoreProductsState
import zlagoda.ukma.edu.ua.screens.store_products.viewmodel.StoreProductsViewModel
import zlagoda.ukma.edu.ua.screens.store_products_search.StoreProductSearchScreen

@Composable
internal fun StoreProductsScreen() {
    StoredViewModel(factory = { StoreProductsViewModel() }) { viewModel ->
        val modalController = LocalRootController.current.findModalController()
        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {
            is StoreProductsState.StoreProductList -> StoreProductViewList(
                state = state,
                onEvent = { viewModel.obtainEvent(it) }
            )

            is StoreProductsState.Loading -> {
                Text("Loading")
            }
        }

        when (val action = viewAction) {
            is StoreProductsAction.OpenEditStoreProductDialog -> modalController.present(alertConfiguration) { key ->
                StoreProductItemDialog(
                    storeProduct = action.storeProduct,
                    idProductToName = action.idProductToName,
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            is StoreProductsAction.OpenNewStoreProductDialog -> modalController.present(alertConfiguration) { key ->
                StoreProductItemDialog(
                    idProductToName = action.idProductToName,
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            is StoreProductsAction.OpenSearchDialog -> modalController.present(alertConfiguration) { key ->
                StoreProductSearchScreen(onCloseClick = { modalController.popBackStack(key) })
            }
            is StoreProductsAction.OpenProductDetailsDialog -> modalController.present(alertConfiguration) { key ->
                StoreProductDetailsDialog(onCloseClick = { modalController.popBackStack(key) })
            }
            null -> {}
        }
    }
}