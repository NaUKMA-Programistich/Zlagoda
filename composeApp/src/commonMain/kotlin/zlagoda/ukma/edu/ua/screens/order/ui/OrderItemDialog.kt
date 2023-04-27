package zlagoda.ukma.edu.ua.screens.order.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.GetAllStoreProductsWithNames
import zlagoda.ukma.edu.ua.db.Sale
import zlagoda.ukma.edu.ua.screens.order.viewmodel.OrderEvent
import java.util.*
import kotlin.math.roundToInt


@Composable
internal fun OrderItemDialog(
    cheque: Cheque = Cheque(
        chequeNumber = "",
        idEmployee = "",
        cardNumber = "",
        printDate = Date(),
        sumTotal = 0.0,
        vat = 0.0
    ),
    idCustomerCardToName: Map<String, String>,
    user: Employee,
    onCloseClick: () -> Unit,
    onEvent: (OrderEvent) -> Unit,
    productMap: Map<String, GetAllStoreProductsWithNames>
) {
    var idCustomerCard by remember { mutableStateOf(cheque.cardNumber) }

    Column(
        modifier = Modifier.fillMaxSize().padding(0.dp,15.dp,15.dp,0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp, 10.dp, 20.dp, 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Check: ${cheque.chequeNumber.ifBlank { "New" }}",
                style = MaterialTheme.typography.h5
            )
            IconButton(onClick = { onCloseClick() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }

        }
        Divider(modifier = Modifier.fillMaxWidth())

        val saleDataList = remember { mutableStateListOf(SaleData()) }

        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(15.dp, 10.dp, 10.dp, 10.dp),
                    text = "Employee:   ${user.empl_name} ${user.empl_surname}",
                    fontSize = 19.sp
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(15.dp, 10.dp, 10.dp, 10.dp),
                    text = "CustomerCard:",
                    fontSize = 19.sp
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }

            item {
                OrderDataWithDropdown(
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    value = idCustomerCardToName[idCustomerCard] ?: "None",
                    dropdownItems = idCustomerCardToName.toDropDownOrderDataItems(),
                    onItemClick = { idCustomerCard = it.id }
                )
            }

            items(saleDataList) { data ->
                saleItemAdder(saleDataList, data, productMap)
            }

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        modifier = Modifier.padding(15.dp),
                        onClick = { saleDataList.add(SaleData()) },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary,
                        )
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add sellOp")
                    }

                    Button(
                        modifier = Modifier.padding(15.dp),
                        onClick = { if (saleDataList.isNotEmpty()) saleDataList.removeLast() },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary,
                        )
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete sellOp")
                    }

                }

            }
        }


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    modifier = Modifier.padding(15.dp),
                    onClick = {
                        var res = saleDataList.filter { it.productNumber.value > 0L }
                        if (res.isNotEmpty()) {
                            val chequeNumber = UUID.randomUUID().toString()
                            onEvent(
                                OrderEvent.SaveOrder(
                                    cheque = Cheque(
                                        chequeNumber = chequeNumber,
                                        idEmployee = user.id_of_employee,
                                        cardNumber = idCustomerCard,
                                        printDate = Date(),
                                        sumTotal = res.sumOf { it.sellingPrice.value },
                                        vat = 0.0,
                                    ),
                                    saleList = res.map {
                                        Sale(
                                            upc = it.upc.value,
                                            chequeNumber = chequeNumber,
                                            productNumber = it.productNumber.value.toLong(),
                                            sellingPrice = it.sellingPrice.value,
                                        )
                                    }
                                )
                            )
                        }
                        onCloseClick()
                    },
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

class SaleData(){
    val upc = mutableStateOf("")
    val productNumber = mutableStateOf(0L)
    val sellingPrice = mutableStateOf(0.0)
}

@Composable
fun saleItemAdder(
    saleDataList: SnapshotStateList<SaleData>,
    data: SaleData,
    products: Map<String, GetAllStoreProductsWithNames>
) {
    Divider(modifier = Modifier.fillMaxWidth())

    Row(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        var product = products[data.upc.value]

        OrderDataWithDropdown(
            modifier = Modifier.weight(1f).padding(5.dp, 8.dp, 5.dp, 5.dp),
            value = if(product != null) "${product.productName} ${if(product.promotionalProduct>0) "Promotional" else ""}" else "None",
            dropdownItems = products.toDropDownProductDataItems(),
            onItemClick = { if (saleDataList.find { dataUpc -> dataUpc.upc.value == it.id } == null) data.upc.value = it.id }
        )

        if (product != null) {
            OutlinedTextField(
                value = data.productNumber.value.toString(),
                onValueChange = {
                    val str = it.replace(Regex("[^\\d]"), "")
                    data.productNumber.value = if (str.isNotEmpty()) str.toLong() else 0L;
                    data.sellingPrice.value = (100 * data.productNumber.value * product.sellingPrice * if (product.promotionalProduct == 1L) 0.8 else 1.0).roundToInt().toDouble()/100.0
                    if (data.productNumber.value > product.productsNumber)
                        data.productNumber.value = product.productsNumber
                },
                label = { Text("ProductNumber") },
                modifier = Modifier.weight(1f).padding(5.dp)
            )

            Text(
                modifier = Modifier.weight(0.5f).padding(5.dp),
                text = "Sum: ${data.sellingPrice.value}",
                fontSize = 18.sp
            )
        }

    }

}

fun Map<String, String>.toDropDownOrderDataItems(): List<OrderDataWithDropdown> {
    return this.toList().map { OrderDataWithDropdown(it.first, it.second) }
}


fun Map<String, GetAllStoreProductsWithNames>.toDropDownProductDataItems(): List<OrderDataWithDropdown> {
    return this.toList().map { OrderDataWithDropdown(
        id = it.first,
        text = "${it.second.productName} ${if(it.second.promotionalProduct>0) "Promotional" else ""}"
    ) }
}

data class OrderDataWithDropdown(
    val id: String = "",
    val text: String
)


@Composable
fun OrderDataWithDropdown(
    value: String,
    dropdownItems: List<OrderDataWithDropdown>,
    modifier: Modifier = Modifier,
    onItemClick: (OrderDataWithDropdown) -> Unit
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Card(
        elevation = 4.dp,
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
    ) {
        Box(
            modifier = Modifier
                //  .height(150.dp)
                .fillMaxWidth()
                .indication(interactionSource, LocalIndication.current)
                .clickable {
                    isContextMenuVisible = true
                }
                .padding(16.dp)
        ) {
            Text(text = value)
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            dropdownItems.forEach {
                DropdownMenuItem(onClick = {
                    onItemClick(it)
                    isContextMenuVisible = false
                }) {
                    Text(text = it.text)
                }
            }
        }
    }
}