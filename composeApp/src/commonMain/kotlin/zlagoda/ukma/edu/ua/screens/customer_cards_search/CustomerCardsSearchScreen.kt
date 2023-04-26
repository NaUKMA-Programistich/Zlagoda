package zlagoda.ukma.edu.ua.screens.customer_cards_search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.core.composable.ComposableError
import zlagoda.ukma.edu.ua.core.composable.ComposableLoading
import zlagoda.ukma.edu.ua.screens.customer_cards_search.ui.CustomerCardSearchList
import zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel.CustomerCardsSearchAction
import zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel.CustomerCardsSearchState
import zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel.CustomerCardsSearchViewModel

@Composable
internal fun CustomerCardsSearchScreen(
    onCloseClick: () -> Unit
) {
    StoredViewModel(factory = { CustomerCardsSearchViewModel() }) { viewModel ->
        val modalController = LocalRootController.current.findModalController()
        val alertConfiguration = AlertConfiguration(maxHeight = 0.15f, maxWidth = 0.3f, cornerRadius = 4)
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.h5
                )
                IconButton(onClick = onCloseClick) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
                }
            }
            when (val state = viewState) {
                CustomerCardsSearchState.Loading -> ComposableLoading()
                is CustomerCardsSearchState.CustomerCardList -> {
                    CustomerCardSearchList(
                        state = state,
                        onEvent = viewModel::obtainEvent
                    )
                }
            }
        }

        when (val action = viewAction) {
            is CustomerCardsSearchAction.ShowError -> modalController.present(alertConfiguration) { key ->
                ComposableError(message = action.message) { modalController.popBackStack(key) }
            }
            null -> {}
        }
    }
}
