package zlagoda.ukma.edu.ua.screens.store_products.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.theme.add_button_color
import zlagoda.ukma.edu.ua.core.theme.delete_button_color
import zlagoda.ukma.edu.ua.core.theme.edit_button_color
import zlagoda.ukma.edu.ua.db.StoreProduct
import zlagoda.ukma.edu.ua.screens.store_products.viewmodel.StoreProductsEvent
import zlagoda.ukma.edu.ua.screens.store_products.viewmodel.StoreProductsState


@Composable
internal fun StoreProductViewList (
    state: StoreProductsState.StoreProductList,
    onEvent: (StoreProductsEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
                onClick = { onEvent(StoreProductsEvent.SearchStoreProduct) }
            ) {
                Text("Search")
            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
                onClick = { onEvent(StoreProductsEvent.CreateNewStoreProduct) }
            ) {
                Text("Add New")
            }
        }

        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(modifier = Modifier.fillMaxSize().draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState { delta ->
                coroutineScope.launch {
                    scrollState.scrollBy(-delta)
                }
            },
        )) {
            items(state.storeProducts) { storeProduct ->
                StoreProductItem(
                    storeProduct = storeProduct,
                    productName = state.idProductToName[storeProduct.idProduct]?: "None",
                    onEditClick = { onEvent(StoreProductsEvent.EditStoreProduct(storeProduct)) },
                    onDeleteClick = { onEvent(StoreProductsEvent.DeleteStoreProduct(storeProduct)) }
                )
            }
        }
    }
}

@Composable
internal fun StoreProductItem(
    modifier: Modifier = Modifier,
    storeProduct: StoreProduct,
    productName: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(color = MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 5.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Category: $productName"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Price: ${storeProduct.sellingPrice}"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Amount: ${storeProduct.productsNumber}"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Promotional: ${if (storeProduct.promotionalProduct>0) "Yes" else "No"}"
        )

        Row(
            modifier = Modifier.width(120.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onEditClick() },
                modifier = Modifier.background(color = edit_button_color, shape = CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(
                onClick = { onDeleteClick() },
                modifier = Modifier.background(color = delete_button_color, shape = CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}