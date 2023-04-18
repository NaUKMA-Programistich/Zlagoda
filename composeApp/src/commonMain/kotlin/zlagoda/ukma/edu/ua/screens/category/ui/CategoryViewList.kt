package zlagoda.ukma.edu.ua.screens.category.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryEvent
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryState


@Composable
internal fun CategoryViewList (
    state: CategoryState.CategoryList,
    onEvent: (CategoryEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { onEvent(CategoryEvent.AddNewCategory) }
        ) {
            Text("Add new")
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.categories) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(CategoryEvent.SelectCategoryItem(it))
                        },
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(150.dp),
                        text = "Name: ${it.name}"
                    )
                    IconButton(
                        onClick = { onEvent(CategoryEvent.DeleteCategory(it)) }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete category")
                    }
                }
            }
        }
    }
}