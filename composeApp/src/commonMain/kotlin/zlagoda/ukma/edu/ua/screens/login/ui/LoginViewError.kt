package zlagoda.ukma.edu.ua.screens.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginEvent
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginState
import zlagoda.ukma.edu.ua.screens.products_search.ui.ProductSearchAppBar
import zlagoda.ukma.edu.ua.screens.products_search.ui.ProductSearchList
import zlagoda.ukma.edu.ua.screens.products_search.viewmodel.ProductsSearchEvent
import zlagoda.ukma.edu.ua.screens.products_search.viewmodel.ProductsSearchState

@Composable
internal fun LoginViewError(message: String, onClose: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(8.dp)
        )
        Button(
            onClick = { onClose() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ok")
        }
    }
}
