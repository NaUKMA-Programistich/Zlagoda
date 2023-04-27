package zlagoda.ukma.edu.ua.screens.customer_cards.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.theme.add_button_color
import zlagoda.ukma.edu.ua.core.theme.delete_button_color
import zlagoda.ukma.edu.ua.core.theme.edit_button_color
import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel.CustomerCardsEvent
import zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel.CustomerCardsState
import zlagoda.ukma.edu.ua.screens.products.viewmodel.ProductsEvent
import zlagoda.ukma.edu.ua.utils.authorization.Authorization


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CustomerCardViewList (
    state: CustomerCardsState.CustomerCardList,
    onEvent: (CustomerCardsEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
                    onClick = { onEvent(CustomerCardsEvent.Search) }
            ) {
                Text("Search")
            }
            if (Authorization.currentUserHasRole("Manager")) {
                Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
                        onClick = { onEvent(CustomerCardsEvent.CreateNewCustomerCard) }
                ) {
                    Text("Add New")
                }
            }
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
            items(state.customerCards) { customerCard ->
                CustomerCardItem(
                    customerCard = customerCard,
                    onEditClick = { onEvent(CustomerCardsEvent.EditCustomerCard(customerCard)) },
                    onDeleteClick = { onEvent(CustomerCardsEvent.DeleteCustomerCard(customerCard)) }
                )
            }
        }
    }
}

@Composable
internal fun CustomerCardItem(
    modifier: Modifier = Modifier,
    customerCard: CustomerCard?,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .background(color = MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 5.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = customerCard?.custSurname ?: "Surname"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = customerCard?.custName ?: "Name"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = customerCard?.custPatronymic ?: "Patronymic"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = customerCard?.cardNumber ?: "Card Number"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = customerCard?.city ?: "City"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = customerCard?.street ?: "Street"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = customerCard?.zipCode ?: "ZipCode"
        )
        val percent = if(customerCard == null) "Percent" else "${customerCard.percent}"
        Text(
            modifier = Modifier.weight(1f),
            text = percent
        )

        Row(
            modifier = Modifier.width(120.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onEditClick() },
                modifier = Modifier.background(color = edit_button_color, shape = CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            if(Authorization.currentUserHasRole("Manager")) {
                IconButton(
                        onClick = { onDeleteClick() },
                        modifier = Modifier.background(color = delete_button_color, shape = CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}