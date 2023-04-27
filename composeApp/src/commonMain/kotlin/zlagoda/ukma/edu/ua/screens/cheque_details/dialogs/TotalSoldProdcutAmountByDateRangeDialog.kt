package zlagoda.ukma.edu.ua.screens.cheque_details.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import zlagoda.ukma.edu.ua.core.composable.DropDownItem
import zlagoda.ukma.edu.ua.core.composable.ItemWithDropdown
import zlagoda.ukma.edu.ua.data.cheque.ChequeRepository
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.db.GetTotalSoldAmountForProduct
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.di.Injection

@Composable
internal fun TotalSoldProdcutAmountByDateRangeDialog(
    onClose: () -> Unit,
    productRepository: ProductRepository = Injection.productRepository,
    chequeRepository: ChequeRepository = Injection.chequeCardRepository
) {
    var dateStartStr by remember { mutableStateOf("2001-01-01") }
    var dateEndStr by remember { mutableStateOf("2031-01-01") }
    var products by remember { mutableStateOf(emptyList<Product>()) }
    var selecetedProductIndex by remember { mutableStateOf(0) }
    val dataState: MutableState< GetTotalSoldAmountForProduct?> = remember { mutableStateOf(null) }

    LaunchedEffect(key1 = true) {
        productRepository.getAllProducts().collectLatest {
            products = it
        }
    }

    LaunchedEffect(products, selecetedProductIndex, dateStartStr, dateEndStr) {
        dataState.value = chequeRepository.getTotalSoldAmountForProduct(
            idProduct = if (products.isNotEmpty()) products[selecetedProductIndex].idProduct else -1,
            startDate = dateStartStr,
            endDate = dateEndStr
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total sold product amount for date range",
                style = MaterialTheme.typography.h5
            )
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (products.isNotEmpty()) {
                val product = products[selecetedProductIndex]
                ItemWithDropdown(
                    modifier = Modifier.width(200.dp),
                    value = product.productName,
                    dropdownItems = products.toDropDownItems(),
                    onItemClick = { selecetedProductIndex = it.id.toInt() }
                )
            }
            OutlinedTextField(
                value = dateStartStr,
                onValueChange = { dateStartStr = it },
                label = { Text("Start Date (yyyy-mm-dd)") },
                modifier = Modifier.width(250.dp).padding(5.dp)
            )
            OutlinedTextField(
                value = dateEndStr,
                onValueChange = { dateEndStr = it },
                label = { Text("End Date (yyyy-mm-dd)") },
                modifier = Modifier.width(250.dp).padding(5.dp)
            )
        }
        Text(
            text = "Total sold amount for this product: ${dataState.value?.totalSoldAmount?:0}"
        )
    }
}


private fun List<Product>.toDropDownItems(): List<DropDownItem> {
    return List(this.size) { DropDownItem(
        id = it.toLong(),
        text = this[it].productName )
    }
}