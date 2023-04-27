package zlagoda.ukma.edu.ua.screens.options.ui.dzhos

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.db.GetProductsNotSoldAndNotPromotionalAndPriceGreatThan

@Composable
internal fun ComposableReportDzhosNot(
    action: List<GetProductsNotSoldAndNotPromotionalAndPriceGreatThan>,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Продукти, які не мають продажів та не є промоційними, з вартістю більшою або дорівнюють заданому параметру",
                style = MaterialTheme.typography.h5,
                fontSize = 16.sp
            )
            IconButton(onClick = onCloseClick) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }

        ComposableGetProductsNotSoldAndNotPromotionalAndPriceLessThan(null)
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        LazyColumn(
            modifier = Modifier.fillMaxSize().draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch { scrollState.scrollBy(-delta) }
               },
        )) {
            items(action.size) { index ->
                ComposableGetProductsNotSoldAndNotPromotionalAndPriceLessThan(action[index])
            }
        }
    }
}

@Composable
private fun ComposableGetProductsNotSoldAndNotPromotionalAndPriceLessThan(
    data: GetProductsNotSoldAndNotPromotionalAndPriceGreatThan?
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .background(color = MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 5.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = data?.idProduct?.toString() ?: "Product Id"
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = data?.productName ?: "Product Name"
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = data?.sellingPrice?.toString() ?: "Price"
        )
    }
}
