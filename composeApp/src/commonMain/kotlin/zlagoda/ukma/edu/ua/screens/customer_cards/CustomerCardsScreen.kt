package zlagoda.ukma.edu.ua.screens.customer_cards

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.screens.customer_cards.ui.CustomerCardItemDialog
import zlagoda.ukma.edu.ua.screens.customer_cards.ui.CustomerCardViewList
import zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel.CustomerCardViewModel
import zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel.CustomerCardsAction
import zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel.CustomerCardsState
import zlagoda.ukma.edu.ua.screens.customer_cards_search.CustomerCardsSearchScreen

@Composable
internal fun CustomerCardsScreen() {
    StoredViewModel(factory = { CustomerCardViewModel() }) { viewModel ->
        val modalController = LocalRootController.current.findModalController()
        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {
            is CustomerCardsState.CustomerCardList -> CustomerCardViewList(
                state = state,
                onEvent = { viewModel.obtainEvent(it) }
            )

            is CustomerCardsState.Loading -> {
                Text("Loading")
            }
        }

        when (val action = viewAction) {
            is CustomerCardsAction.OpenEditCustomerCardDialog -> modalController.present(alertConfiguration) { key ->
                CustomerCardItemDialog(
                    customerCard = action.customerCard,
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            is CustomerCardsAction.OpenNewCustomerCardDialog -> modalController.present(alertConfiguration) { key ->
                CustomerCardItemDialog(
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            null -> {}
            CustomerCardsAction.OpenSearch -> modalController.present(alertConfiguration) { key ->
                CustomerCardsSearchScreen(onCloseClick = { modalController.popBackStack(key) })
            }
        }
    }
}