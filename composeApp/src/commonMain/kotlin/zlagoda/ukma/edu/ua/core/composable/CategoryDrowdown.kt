package zlagoda.ukma.edu.ua.core.composable

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.db.Category

@Composable
internal fun CategoryDropdown(
    current: Category?,
    dropdownItems: List<Category>,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false,
    onItemClick: (Category?) -> Unit
) {
    var isContextMenuVisible by rememberSaveable { mutableStateOf(false) }
    val pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val interactionSource = remember { MutableInteractionSource() }
    val density = LocalDensity.current

    Card(
        elevation = 4.dp,
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
    ) {
        Box(
            modifier = Modifier
                .indication(interactionSource, LocalIndication.current)
                .clickable {
                    if (isEnabled) isContextMenuVisible = true
                }
                .padding(16.dp)
        ) {
            Text(
                text = current?.name ?: "Choose Product Category",
                textAlign = TextAlign.Right
            )
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            dropdownItems.forEach {
                DropdownMenuItem(onClick = {
                    onItemClick(it)
                    isContextMenuVisible = false
                }) {
                    Text(text = it.name)
                }
            }
            DropdownMenuItem(onClick = {
                onItemClick(null)
                isContextMenuVisible = false
            }) {
                Text(text = "-")
            }
        }
    }
}