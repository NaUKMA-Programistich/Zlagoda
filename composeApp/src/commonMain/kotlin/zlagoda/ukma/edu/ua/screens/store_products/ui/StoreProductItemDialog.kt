package zlagoda.ukma.edu.ua.screens.store_products.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.core.composable.DropDownItem
import zlagoda.ukma.edu.ua.core.composable.ItemWithDropdown
import zlagoda.ukma.edu.ua.db.StoreProduct
import zlagoda.ukma.edu.ua.screens.store_products.viewmodel.StoreProductsEvent

@Composable
internal fun StoreProductItemDialog(
    storeProduct: StoreProduct = StoreProduct("", "", -1, 0.0, 0, 0),
    idProductToName: Map<Long, String>,
    onCloseClick: () -> Unit,
    onEvent:  (StoreProductsEvent) -> Unit
) {
    var idProduct by remember { mutableStateOf(storeProduct.idProduct) }
    var sellingPrice by remember { mutableStateOf(storeProduct.sellingPrice) }
    var productsNumber by remember { mutableStateOf(storeProduct.productsNumber) }
    var promotionalProduct by remember { mutableStateOf(storeProduct.promotionalProduct) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "StoreProducts: ${storeProduct.upc.ifBlank { "New" }}",
                style = MaterialTheme.typography.h5
            )
            IconButton(onClick = { onCloseClick() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                ItemWithDropdown(
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    value = idProductToName[idProduct]?: "None",
                    dropdownItems = idProductToName.toDropDownItems(),
                    onItemClick = { idProduct = it.id }
                )
            }
            item {
                OutlinedTextField(
                    value = sellingPrice.toString(),
                    onValueChange = { sellingPrice = it.toDouble() },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = productsNumber.toString(),
                    onValueChange = {  productsNumber = it.toLong() },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Promotional")
                    Checkbox(
                        checked = promotionalProduct > 0,
                        onCheckedChange = { promotionalProduct = if (it) 1 else 0 }
                    )
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    onEvent(StoreProductsEvent.SaveStoreProduct(
                        StoreProduct(
                            storeProduct.upc,
                            storeProduct.upcProm,
                            idProduct,
                            sellingPrice,
                            productsNumber,
                            promotionalProduct
                        )
                    ))
                    onCloseClick()
                },
            ) {
                Text("Save")
            }
        }
    }

}
private fun Map<Long, String>.toDropDownItems(): List<DropDownItem> {
    return this.toList().map { DropDownItem(it.first, it.second) }
}

