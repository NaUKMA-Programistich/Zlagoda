package zlagoda.ukma.edu.ua.screens.options.ui.melnyk

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.db.Employee

@Composable
internal fun ComposableReportMelnykNot(
    action: List<String>,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Категорї в яких ще не було покупок та категорії до яких входять товари з Gaming",
                style = MaterialTheme.typography.h5,
                fontSize = 16.sp
            )
            IconButton(onClick = onCloseClick) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())
        LazyColumn {
            items(action) {
                Row(modifier = Modifier.fillMaxWidth().padding(5.dp).height(35.dp)){
                    Text(modifier = Modifier.padding(5.dp),text = it,
                        fontSize = 18.sp)
                }
            }
        }
    }
}

