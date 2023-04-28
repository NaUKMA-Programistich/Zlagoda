package zlagoda.ukma.edu.ua.screens.options.ui.melnyk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zlagoda.ukma.edu.ua.db.GetListSellersRankAndTheirTopCategoriesRank

@Composable
internal fun ComposableReportMelnykGroup(
    action: List<GetListSellersRankAndTheirTopCategoriesRank>,
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
                text = "Список рейтингу продавців та категорій в якій вони робили продажі",
                style = MaterialTheme.typography.h5,
                fontSize = 16.sp
            )
            IconButton(onClick = onCloseClick) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }

        GetListSellersRankAndTheirTopCategoriesRankItem(null)
        LazyColumn {
            items(action) {
                GetListSellersRankAndTheirTopCategoriesRankItem(it)
            }
        }

    }
}


@Composable
private fun GetListSellersRankAndTheirTopCategoriesRankItem(
    data: GetListSellersRankAndTheirTopCategoriesRank?
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .background(color = MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 5.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = data?.empl_surname ?: "Surname"
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = data?.empl_name?: "Name"
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text =   data?.name?: "Category"
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = data?.productCount?.toString()?: "Sales Amount"
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = data?.totalSales?.toString()?: "Total Sum"
        )
    }
}
