package zlagoda.ukma.edu.ua.screens.products

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.screens.products.ui.ProductItemDialog
import zlagoda.ukma.edu.ua.screens.products.ui.ProductViewList
import zlagoda.ukma.edu.ua.screens.products.viewmodel.ProductsAction
import zlagoda.ukma.edu.ua.screens.products.viewmodel.ProductsState
import zlagoda.ukma.edu.ua.screens.products.viewmodel.ProductsViewModel

@Composable
internal fun ProductsScreen() {
    StoredViewModel(factory = { ProductsViewModel() }) { viewModel ->
        val modalController = LocalRootController.current.findModalController()
        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {
            is ProductsState.ProductList -> ProductViewList(
                state = state,
                onEvent = { viewModel.obtainEvent(it) }
            )

            is ProductsState.Loading -> {
                Text("Loading")
            }
        }

        when (val action = viewAction) {
            is ProductsAction.OpenEditProductDialog -> modalController.present(alertConfiguration) { key ->
                ProductItemDialog(
                    product = action.product,
                    categoryNumberToName = action.categoryNumberToName,
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            is ProductsAction.OpenNewProductDialog -> modalController.present(alertConfiguration) { key ->
                ProductItemDialog(
                    categoryNumberToName = action.categoryNumberToName,
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            null -> {}
        }
    }
}