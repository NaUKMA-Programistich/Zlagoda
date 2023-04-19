package zlagoda.ukma.edu.ua.screens.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryEvent
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryState


@Composable
internal fun CategoryViewList (
    state: CategoryState.CategoryList,
    onEvent: (CategoryEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.End) {
            Button(
                onClick = { onEvent(CategoryEvent.CreateNewCategory) }
            ) {
                Text("Add New")
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.categories) { category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(20.dp))
                        .padding(vertical = 5.dp, horizontal = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Name: ${category.name}"
                    )
                    Row(
                        modifier = Modifier.width(120.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { onEvent(CategoryEvent.EditCategory(category)) },
                            modifier = Modifier.background(color = Color.Blue, shape = CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit category")
                        }
                        IconButton(
                            onClick = { onEvent(CategoryEvent.DeleteCategory(category)) },
                            modifier = Modifier.background(color = Color.Red, shape = CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete category")
                        }
                    }
                }
            }
        }
    }
}