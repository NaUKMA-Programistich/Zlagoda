package zlagoda.ukma.edu.ua.screens.customer_cards_search.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.ktx.isManager
import zlagoda.ukma.edu.ua.core.ktx.isSeller
import zlagoda.ukma.edu.ua.screens.customer_cards.ui.CustomerCardItem
import zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel.CustomerCardsSearchEvent
import zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel.CustomerCardsSearchState

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CustomerCardSearchList(
    state: CustomerCardsSearchState.CustomerCardList,
    onEvent: (CustomerCardsSearchEvent) -> Unit,
) {
    val customerCards = state.customerCard
    val employee = state.currentEmployee
    val text = when {
        employee.isManager() -> "Search by card percent"
        else -> "Search by surname"
    }

    var value by remember { mutableStateOf("") }
    LaunchedEffect(value) {
        when {
            employee.isManager() -> onEvent(CustomerCardsSearchEvent.Percent(value))
            employee.isSeller() -> onEvent(CustomerCardsSearchEvent.Surname(value))
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            label = { Text(text) },
            modifier = Modifier.padding(5.dp)
        )
    }

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    CustomerCardItem(Modifier, null, {}, {})
    LazyColumn(modifier = Modifier.fillMaxSize().draggable(
        orientation = Orientation.Horizontal,
        state = rememberDraggableState { delta ->
            coroutineScope.launch {
                scrollState.scrollBy(-delta)
            }
        },
    )) {
        items(customerCards) {
            CustomerCardSearchItem(it)
        }
    }
}