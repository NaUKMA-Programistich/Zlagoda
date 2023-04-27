package zlagoda.ukma.edu.ua.screens.order.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeEvent
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderEvent
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderState

@Composable
fun OrderList(state: OrderState.OrderList,
              onEvent: (OrderEvent) -> Unit) {




    Column (modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally){

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 450.dp)
        ) {
                for (cheque in state.chequesWithSalesMap){
                    item {
                        OrderCard(user = state.currentEmployee, getChequesDataList = cheque.value, onEvent = onEvent)
                    }
                }
        }



        Button(
            onClick = { onEvent(OrderEvent.CreateNewOrder) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Add Order")
        }
    }



}