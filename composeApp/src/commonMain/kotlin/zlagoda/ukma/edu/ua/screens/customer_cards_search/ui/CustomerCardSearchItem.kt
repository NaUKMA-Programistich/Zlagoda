package zlagoda.ukma.edu.ua.screens.customer_cards_search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.db.CustomerCard

@Composable
fun CustomerCardSearchItem(
    customerCard: CustomerCard?
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
    }
}