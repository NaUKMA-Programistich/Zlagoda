package zlagoda.ukma.edu.ua.screens.order

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.core.composable.ComposableLoading
import zlagoda.ukma.edu.ua.screens.order.ui.OrderItemDialog
import zlagoda.ukma.edu.ua.screens.order.ui.OrderList
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderAction
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderState
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderViewModel


@Composable
internal fun OrderScreen() {
    StoredViewModel(factory = { OrderViewModel() }) { viewModel ->
        val modalController = LocalRootController.current.findModalController()
        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {

            is OrderState.OrderList -> OrderList(state = state, onEvent = { viewModel.obtainEvent(it)})

            is OrderState.Loading -> {
                ComposableLoading()
            }

            else -> {}
        }

        when (val action = viewAction) {
            is OrderAction.OpenNewOrderDialog -> modalController.present(alertConfiguration) { key ->
                OrderItemDialog(
                    idCustomerCardToName = action.customerCardsData,
                    user = action.currentEmployee,
                    productMap = action.productMap,
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            is OrderAction.OpenSearchDialog -> modalController.present(alertConfiguration) { key ->

            }
            null -> {}
            else -> {}
        }
    }
}