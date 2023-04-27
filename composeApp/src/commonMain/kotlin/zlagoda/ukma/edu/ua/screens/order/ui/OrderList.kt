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
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.screens.cheque_details.dialogs.*
import zlagoda.ukma.edu.ua.screens.cheque_details.dialogs.DetailsByDateRangeDialog
import zlagoda.ukma.edu.ua.screens.cheque_details.dialogs.DetailsBySellerAndDateRangeDialog
import zlagoda.ukma.edu.ua.screens.cheque_details.dialogs.TotalSalesAmountForSellerInDateRange
import zlagoda.ukma.edu.ua.screens.cheque_details.dialogs.TotalSoldProdcutAmountByDateRangeDialog
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeEvent
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderEvent
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderState
import zlagoda.ukma.edu.ua.utils.authorization.Authorization
/*
 val modalController = LocalRootController.current.findModalController()
    val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
            modalController.present(alertConfiguration) { key ->
                DetailsBySellerAndDateRangeDialog(onClose = { modalController.popBackStack(key) })
            }
        }) {
            Text("Cheques detals by seller and date range")
        }
        Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
            modalController.present(alertConfiguration) { key ->
                DetailsByDateRangeDialog(onClose = { modalController.popBackStack(key) })
            }
        }) {
            Text("Cheques details for date range")
        }
        Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
            modalController.present(alertConfiguration) { key ->
                TotalSoldProdcutAmountByDateRangeDialog(onClose = { modalController.popBackStack(key) })
            }
        }){
            Text("Total sold product amount for date range")
        }

        Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
            modalController.present(alertConfiguration) { key ->
                TotalSalesAmountForSellerInDateRange(onClose = { modalController.popBackStack(key) })
            }
        }) {
            Text("Total sales amount for seller in date range")
        }
        Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
            modalController.present(alertConfiguration) { key ->
                AllChequesBySellerInDateRange(onClose = { modalController.popBackStack(key) })
            }
        }) {
            Text("Cheques by seller and date range")
        }
        Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
            modalController.present(alertConfiguration) { key ->
                DetailsBySellerForToday(onClose = { modalController.popBackStack(key) })
            }
        }) {
            Text("Cheques by seller for today")
        }
        Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
            modalController.present(alertConfiguration) { key ->
                CheckDetailedDescription(onClose = { modalController.popBackStack(key) })
            }
        }) {
            Text("Cheque details by check number")
        }
    }

 */
@Composable
fun OrderList(state: OrderState.OrderList,
              onEvent: (OrderEvent) -> Unit,
) {


    val modalController = LocalRootController.current.findModalController()
    val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)

    Column (modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally){

        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                modalController.present(alertConfiguration) { key ->
                    DetailsBySellerAndDateRangeDialog(onClose = { modalController.popBackStack(key) })
                }
            }) {
                Text("Cheques detals by seller and date range")
            }
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                modalController.present(alertConfiguration) { key ->
                    DetailsByDateRangeDialog(onClose = { modalController.popBackStack(key) })
                }
            }) {
                Text("Cheques details for date range")
            }
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                modalController.present(alertConfiguration) { key ->
                    TotalSoldProdcutAmountByDateRangeDialog(onClose = { modalController.popBackStack(key) })
                }
            }){
                Text("Total sold product amount for date range")
            }
        }

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                modalController.present(alertConfiguration) { key ->
                    TotalSalesAmountForSellerInDateRange(onClose = { modalController.popBackStack(key) })
                }
            }) {
                Text("Total sales amount for seller in date range")
            }
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                modalController.present(alertConfiguration) { key ->
                    AllChequesBySellerInDateRange(onClose = { modalController.popBackStack(key) })
                }
            }) {
                Text("Cheques by seller and date range")
            }
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                modalController.present(alertConfiguration) { key ->
                    DetailsBySellerForToday(onClose = { modalController.popBackStack(key) })
                }
            }) {
                Text("Cheques by seller for today")
            }
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                modalController.present(alertConfiguration) { key ->
                    CheckDetailedDescription(onClose = { modalController.popBackStack(key) })
                }
            }) {
                Text("Cheque details by check number")
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 450.dp)
        ) {
                for (cheque in state.chequesWithSalesMap){
                    item {
                        OrderCard(user = state.currentEmployee, getChequesDataList = cheque.value, onEvent = onEvent)
                    }
                }
        }



        if(Authorization.currentUserHasRole("Seller")) {
            Button(
                onClick = { onEvent(OrderEvent.CreateNewOrder) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Add Order")
            }
        }
    }



}