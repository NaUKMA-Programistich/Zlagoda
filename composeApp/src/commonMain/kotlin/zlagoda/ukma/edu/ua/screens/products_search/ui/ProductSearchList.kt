package zlagoda.ukma.edu.ua.screens.products_search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.core.composable.CategoryDropdown
import zlagoda.ukma.edu.ua.core.ktx.isManager
import zlagoda.ukma.edu.ua.core.ktx.isSeller
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginViewModel
import zlagoda.ukma.edu.ua.screens.products_search.viewmodel.ProductsSearchState

@Composable
internal fun ProductSearchList(
    state: ProductsSearchState.ProductList,
    onSearch: (String, Category?) -> Unit,
) {
    val products = state.products
    val categories = state.categories
    val currentEmployee = state.currentEmployee

    var productName by remember { mutableStateOf("") }
    var categorySelect by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(productName, categorySelect) {
        onSearch(productName, categorySelect)
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
        // Менеджер  та касир
        CategoryDropdown (
            current = categorySelect,
            dropdownItems = categories,
            isEnabled = productName.isEmpty() && (currentEmployee.isSeller() || currentEmployee.isManager()),
            onItemClick = { categorySelect = it },
        )

    }
    LazyColumn {
        items(products) { product ->
            val category = categories.first { it.id == product.categoryNumber }
            ProductSearchItem(
                product = product,
                category = category
            )
        }
    }
}