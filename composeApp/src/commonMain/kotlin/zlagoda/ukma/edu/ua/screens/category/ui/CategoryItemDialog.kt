package zlagoda.ukma.edu.ua.screens.category.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.screens.category.viewmodel.CategoryEvent
import zlagoda.ukma.edu.ua.utils.validation.CategoryValidator
import zlagoda.ukma.edu.ua.utils.validation.InvalidModelException

@Composable
internal fun CategoryItemDialog(
    category: Category = Category(-1, ""),
    onCloseClick: () -> Unit,
    onEvent:  (CategoryEvent) -> Unit
) {
    val nameState = remember { mutableStateOf(category.name) }

    val validator = CategoryValidator()
    val validate = validator::validate
    var errorText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Category: ${if (category.id != 0L) category.name else "New"}",
                style = MaterialTheme.typography.h5
            )
            IconButton(onClick = { onCloseClick() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                OutlinedTextField(
                    value = nameState.value,
                    onValueChange = { nameState.value = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = errorText, color = Color.Red)
                Button(
                    onClick = {
                        val categoryToSave = Category(category.id, nameState.value)
                        try {
                            validate(categoryToSave)
                            onEvent(CategoryEvent.SaveCategory(categoryToSave))
                            errorText = ""
                            onCloseClick()
                        } catch (exception: InvalidModelException) {
                            errorText = exception.message?: ""
                        }
                    },
                ) {
                    Text("Save")
                }
            }
        }
    }

}