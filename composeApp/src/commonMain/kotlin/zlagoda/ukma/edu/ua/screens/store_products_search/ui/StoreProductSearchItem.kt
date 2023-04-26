package zlagoda.ukma.edu.ua.screens.store_products_search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.db.StoreProduct

@Composable
internal fun StoreProductSearchItem(
    storeProduct: StoreProduct,
    product: Product,
    category: Category
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(color = MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 10.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "UPC: ${storeProduct.upc}"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Category: ${category.name}"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Name: ${product.productName}"
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
            text = "Promotional: ${if (storeProduct.promotionalProduct > 0) "Yes" else "No"}"
        )
    }
}