package zlagoda.ukma.edu.ua.screens.store_products_search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.core.composable.CategoryDropdown
import zlagoda.ukma.edu.ua.core.ktx.isManager
import zlagoda.ukma.edu.ua.core.ktx.isSeller
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginViewModel
import zlagoda.ukma.edu.ua.screens.store_products_search.viewmodel.StoreProductsSearchState

@Composable
internal fun StoreProductSearchList(
    state: StoreProductsSearchState.StoreProductList,
    onSearch: (String, Category?, String) -> Unit
) {
    val storeProducts = state.storeProducts
    val products = state.products
    val categories = state.categories
    val currentEmployee = state.currentEmployee

    var productName by remember { mutableStateOf("") }
    var productUPC by remember { mutableStateOf("") }
    var categorySelect by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(productName, categorySelect, productUPC) {
        onSearch(productName, categorySelect, productUPC)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Касир
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Search Product Name") },
            enabled = categorySelect == null && currentEmployee.isSeller(),
            modifier = Modifier.padding(5.dp)
        )

        OutlinedTextField(
            value = productUPC,
            onValueChange = { productUPC = it },
            label = { Text("Exact Product UPC") },
            enabled = categorySelect == null,
            modifier = Modifier.padding(5.dp)
        )

        // Менеджер  та касир
        CategoryDropdown (
            current = categorySelect,
            dropdownItems = categories,
            isEnabled = productName.isEmpty() && (currentEmployee.isSeller() || currentEmployee.isManager()),
            onItemClick = { categorySelect = it },
        )

    }
    LazyColumn {
        items(storeProducts) { storeProduct ->
            val product = products.first { it.idProduct == storeProduct.idProduct }
            val category = categories.first { it.id == product.categoryNumber }
            StoreProductSearchItem(
                storeProduct = storeProduct,
                product = product,
                category = category
            )
        }
    }
}