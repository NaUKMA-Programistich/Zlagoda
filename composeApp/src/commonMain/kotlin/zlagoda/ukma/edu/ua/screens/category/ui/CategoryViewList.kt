package zlagoda.ukma.edu.ua.screens.category.ui

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.theme.add_button_color
import zlagoda.ukma.edu.ua.core.theme.delete_button_color
import zlagoda.ukma.edu.ua.core.theme.edit_button_color
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryEvent
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryState
import zlagoda.ukma.edu.ua.utils.authorization.Authorization


@Composable
internal fun CategoryViewList (
    state: CategoryState.CategoryList,
    onEvent: (CategoryEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.End) {
            if(Authorization.currentUserHasRole("Manager")) {
                Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
                        onClick = { onEvent(CategoryEvent.CreateNewCategory) }
                ) {
                    Text("Add New")
                }
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
            items(state.categories) { category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(color = MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(20.dp))
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
                        if(Authorization.currentUserHasRole("Manager")) {
                            IconButton(
                                onClick = { onEvent(CategoryEvent.EditCategory(category)) },
                                modifier = Modifier.background(color = edit_button_color, shape = CircleShape)
                            ) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit category")
                            }
                            IconButton(
                                    onClick = {
                                              if(!state.products.any { it.idProduct == category.id }) {
                                                  onEvent(CategoryEvent.DeleteCategory(category))
                                              }
                                    },
                                    modifier = Modifier
                                        .background(color = if(!state.products.any { it.idProduct == category.id }) delete_button_color else Color.Gray, shape = CircleShape)
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete category")
                            }
                        }
                    }
                }
            }
        }
    }
}