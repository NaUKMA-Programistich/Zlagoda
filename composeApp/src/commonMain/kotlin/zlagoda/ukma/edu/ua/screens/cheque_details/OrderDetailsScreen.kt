package zlagoda.ukma.edu.ua.screens.cheque_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.screens.category.ui.CategoryItemDialog
import zlagoda.ukma.edu.ua.screens.cheque_details.dialogs.DetailsByDateRangeDialog
import zlagoda.ukma.edu.ua.screens.cheque_details.dialogs.DetailsBySellerAndDateRangeDialog
import zlagoda.ukma.edu.ua.screens.cheque_details.dialogs.TotalSoldProdcutAmountByDateRangeDialog

@Composable
internal fun OrderDetailsScreen() {
    val modalController = LocalRootController.current.findModalController()
    val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            modalController.present(alertConfiguration) { key ->
                DetailsBySellerAndDateRangeDialog(onClose = { modalController.popBackStack(key) })
            }
        }) {
            Text("Cheques detals by seller and date range")
        }
        Button(onClick = {
            modalController.present(alertConfiguration) { key ->
                DetailsByDateRangeDialog(onClose = { modalController.popBackStack(key) })
            }
        }) {
            Text("Cheques details for date range")
        }
        Button(onClick = {
            modalController.present(alertConfiguration) { key ->
                TotalSoldProdcutAmountByDateRangeDialog(onClose = { modalController.popBackStack(key) })
            }
        }){
            Text("Total sold product amount for date range")
        }
        Button(onClick = {}) {
            Text("Total sales amount for seller in date range")
        }
        Button(onClick = {}) {
            Text("Cheques by seller and date range")
        }
        Button(onClick = {}) {
            Text("Cheques by seller for today")
        }
        Button(onClick = {}) {
            Text("Cheque details by check number")
        }
    }
}