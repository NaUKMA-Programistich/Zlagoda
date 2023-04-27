package zlagoda.ukma.edu.ua.screens.order.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeEvent
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderEvent
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderState

@Composable
fun OrderList(state: OrderState.OrderList,
              onEvent: (OrderEvent) -> Unit) {

    Button(
        onClick = { onEvent(OrderEvent.CreateNewOrder) },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Add Order")
    }

}