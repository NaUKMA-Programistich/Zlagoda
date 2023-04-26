package zlagoda.ukma.edu.ua.screens.customer_cards.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel.CustomerCardsEvent
import zlagoda.ukma.edu.ua.utils.validation.CustomerCardValidator
import zlagoda.ukma.edu.ua.utils.validation.InvalidModelException

@Composable
internal fun CustomerCardItemDialog(
    customerCard: CustomerCard = CustomerCard("", "", "", null, "", "", "", "", 0),
    onCloseClick: () -> Unit,
    onEvent:  (CustomerCardsEvent) -> Unit
) {
    var custSurname by remember { mutableStateOf(customerCard.custSurname) }
    var custName by remember { mutableStateOf(customerCard.custName) }
    var custPatronymic by remember { mutableStateOf(customerCard.custPatronymic) }
    var phoneNumber by remember { mutableStateOf(customerCard.phoneNumber) }
    var city by remember { mutableStateOf(customerCard.city) }
    var street by remember { mutableStateOf(customerCard.street) }
    var zipCode by remember { mutableStateOf(customerCard.zipCode) }
    var percent by remember { mutableStateOf(customerCard.percent) }

    val validator = CustomerCardValidator()
    val validate = validator::validate
    var errorText by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Customer Card: ${if (customerCard.cardNumber.isBlank()) customerCard.custSurname else "New"}",
                style = MaterialTheme.typography.h5
            )
            IconButton(onClick = { onCloseClick() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                OutlinedTextField(
                    value = custSurname,
                    onValueChange = { custSurname = it },
                    label = { Text("Surname") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = custName,
                    onValueChange = { custName = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = custPatronymic ?: "",
                    onValueChange = { custPatronymic = it },
                    label = { Text("Patronymic") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = city?: "",
                    onValueChange = { city = it },
                    label = { Text("City") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = street?: "",
                    onValueChange = { street = it },
                    label = { Text("Street") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = zipCode?: "",
                    onValueChange = { zipCode = it },
                    label = { Text("Zip Code") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = percent.toString(),
                    onValueChange = { percent = it.toLongOrNull()?: -1 },
                    label = { Text("Percent") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = errorText, color = Color.Red)
                Button(
                    onClick = {
                        try {
                            val customerCardToSave = CustomerCard(
                                cardNumber = customerCard.cardNumber,
                                custSurname = custSurname,
                                custName = custName,
                                custPatronymic = custPatronymic,
                                phoneNumber = phoneNumber,
                                city = city,
                                street = street,
                                zipCode = zipCode,
                                percent = percent
                            )
                            validate(customerCardToSave)
                            onEvent(CustomerCardsEvent.SaveCustomerCard(customerCardToSave))
                            onCloseClick()

                        } catch (exception: InvalidModelException) {
                            errorText = exception.message?: ""
                        }
                    },
                ) {
                    Text("Save")
                }
            }
        }
    }
}

