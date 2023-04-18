package zlagoda.ukma.edu.ua.screens.category.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryEvent
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryState
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginEvent


@Composable
internal fun CategoryViewItem(
    state: CategoryState.CategoryItem,
    onEvent: (CategoryEvent) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Category",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = state.name,
            onValueChange = { name -> onEvent(CategoryEvent.SetCategoryName(name)) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Row {
            Button(
                onClick = { onEvent(CategoryEvent.BackToCategoryList) }
            ) {
                Text("Back")
            }
            Button(
                onClick = { onEvent(CategoryEvent.SaveCategory(state.name) )}
            ) {
                Text("Save")
            }
        }
    }
}