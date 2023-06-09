package zlagoda.ukma.edu.ua.screens.products.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.core.composable.DropDownItem
import zlagoda.ukma.edu.ua.core.composable.ItemWithDropdown
import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel.CustomerCardsEvent
import zlagoda.ukma.edu.ua.screens.products.viewmodel.ProductsEvent
import zlagoda.ukma.edu.ua.utils.validation.CustomerCardValidator
import zlagoda.ukma.edu.ua.utils.validation.InvalidModelException
import zlagoda.ukma.edu.ua.utils.validation.ProductValidator

@Composable
internal fun ProductItemDialog(
    product: Product = Product(-1, -1, "", ""),
    categoryNumberToName: Map<Long, String>,
    onCloseClick: () -> Unit,
    onEvent:  (ProductsEvent) -> Unit
) {
    var categoryNumber by remember { mutableStateOf(product.categoryNumber) }
    var productName by remember { mutableStateOf(product.productName) }
    var characteristics by remember { mutableStateOf(product.characteristics) }

    val validator = ProductValidator()
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
                text = "Products: ${if (product.idProduct < 0) product.productName else "New"}",
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
                    value = categoryNumberToName[categoryNumber]?: "None",
                    dropdownItems = categoryNumberToName.toDropDownItems(),
                    onItemClick = { categoryNumber = it.id }
                )
            }
            item {
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = characteristics,
                    onValueChange = { characteristics = it },
                    label = { Text("Characteristics") },
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
                            val productToSave = Product(
                                product.idProduct,
                                categoryNumber,
                                productName,
                                characteristics
                            )
                            validate(productToSave)
                            onEvent(ProductsEvent.SaveProduct(productToSave))
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
fun Map<Long, String>.toDropDownItems(): List<DropDownItem> {
    return this.toList().map { DropDownItem(it.first, it.second) }
}

