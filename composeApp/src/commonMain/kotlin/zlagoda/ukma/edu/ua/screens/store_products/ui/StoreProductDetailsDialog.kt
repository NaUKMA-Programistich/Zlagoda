package zlagoda.ukma.edu.ua.screens.store_products.ui

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
import zlagoda.ukma.edu.ua.core.composable.DropDownItem
import zlagoda.ukma.edu.ua.core.composable.ItemWithDropdown
import zlagoda.ukma.edu.ua.data.store_product.StoreProductRepository
import zlagoda.ukma.edu.ua.db.GetStoreProductDescriptionByUPC
import zlagoda.ukma.edu.ua.db.StoreProduct
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.screens.store_products.viewmodel.StoreProductsEvent
import zlagoda.ukma.edu.ua.utils.validation.InvalidModelException
import zlagoda.ukma.edu.ua.utils.validation.StoreProductValidator

@Composable
internal fun StoreProductDetailsDialog(
    onCloseClick: () -> Unit,
    storeProductsRepository: StoreProductRepository = Injection.storeProductRepository
) {
    var upc by remember { mutableStateOf("") }
    var stroreProductDesc: GetStoreProductDescriptionByUPC? by remember { mutableStateOf(null) }


    LaunchedEffect(upc) {
        stroreProductDesc = storeProductsRepository.getStoreProductDescriptionByUPC(upc)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Store Products Details",
                style = MaterialTheme.typography.h5
            )
            IconButton(onClick = { onCloseClick() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = upc,
                onValueChange = {  upc = it },
                label = { Text("UPC") },
                modifier = Modifier.fillMaxWidth().padding(5.dp)
            )
        }
        LazyColumn (modifier = Modifier.weight(1f)) {
            item {
                stroreProductDesc?.let {
                    StoreProductDescItem(it)
                }
            }
        }
    }

}

@Composable
fun StoreProductDescItem(
    stroreProductDesc: GetStoreProductDescriptionByUPC
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Name: ${stroreProductDesc.productName}")
        Text("Price: ${stroreProductDesc.sellingPrice}")
        Text("Amount: ${stroreProductDesc.productsNumber}")
        Text("Characteristics: ${stroreProductDesc.characteristics}")
    }
}




