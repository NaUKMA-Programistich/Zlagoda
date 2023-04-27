package zlagoda.ukma.edu.ua.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soywiz.klock.DateTime
import com.soywiz.klock.days
import com.soywiz.klock.months
import zlagoda.ukma.edu.ua.core.ktx.wrap

@Composable
internal fun Calendar(
    selectedDate: DateTime,
    onDateSelected: (DateTime) -> Unit,
    onCloseClick: () -> Unit,
) {
    val dayOfWeekColor: Color = MaterialTheme.colors.primary
    val selectedColor: Color = MaterialTheme.colors.secondary
    val textColor = MaterialTheme.colors.onSurface

    val allowSameDate = false
    val cellSize: Dp = 40.dp
    var dateState by remember { mutableStateOf(selectedDate) }

    Column(
        modifier = Modifier.background(MaterialTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("${dateState.month.localShortName} ${dateState.year.year}")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier.clickable { dateState = dateState.minus(1.months) },
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Month Back"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier.clickable { dateState = dateState.plus(1.months) },
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Month Forward"
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onCloseClick() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.weight(1f).height(cellSize),
                textAlign = TextAlign.Center,
                text = "MON",
                color = dayOfWeekColor
            )
            Text(
                modifier = Modifier.weight(1f).height(cellSize),
                textAlign = TextAlign.Center,
                text = "TUE",
                color = dayOfWeekColor
            )
            Text(
                modifier = Modifier.weight(1f).height(cellSize),
                textAlign = TextAlign.Center,
                text = "WED",
                color = dayOfWeekColor
            )
            Text(
                modifier = Modifier.weight(1f).height(cellSize),
                textAlign = TextAlign.Center,
                text = "THU",
                color = dayOfWeekColor
            )
            Text(
                modifier = Modifier.weight(1f).height(cellSize),
                textAlign = TextAlign.Center,
                text = "FRI",
                color = dayOfWeekColor
            )
            Text(
                modifier = Modifier.weight(1f).height(cellSize),
                textAlign = TextAlign.Center,
                text = "SAT",
                color = dayOfWeekColor
            )
            Text(
                modifier = Modifier.weight(1f).height(cellSize),
                textAlign = TextAlign.Center,
                text = "SUN",
                color = dayOfWeekColor
            )
        }

        DatesForMonth(textColor, dateState, cellSize, selectedColor) {
            dateState =
                DateTime.fromString("${dateState.yearInt}-${dateState.month1.wrap()}-${it.wrap()}").local
        }

        val isSame by derivedStateOf {
            selectedDate.yearInt == dateState.yearInt &&
                    selectedDate.month0 == dateState.month0 &&
                    selectedDate.dayOfMonth == dateState.dayOfMonth
        }

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            onClick = { onDateSelected.invoke(dateState) }) {
            Text("Select")
        }
    }
}

@Composable
internal fun DatesForMonth(
    textColor: Color,
    date: DateTime,
    cellSize: Dp,
    selectedColor: Color,
    onDateClicked: (Int) -> Unit
) {

    val firstDayOfMonth by derivedStateOf {
        val dayOfWeek = date.minus((date.dayOfMonth - 1).days)
        dayOfWeek.dayOfWeekInt
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.Center,
        userScrollEnabled = false
    ) {
        for (i in 1 until (date.month.days(date.year.year) + firstDayOfMonth)) {
            val dateValue = if (i < firstDayOfMonth) "" else "${i - firstDayOfMonth + 1}"
            val isSelectedDate =
                dateValue.isNotBlank() && dateValue == date.dayOfMonth.toString()

            item {
                var modifier = Modifier.size(cellSize)
                if (isSelectedDate) {
                    modifier = modifier.then(
                        Modifier.background(
                            selectedColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(cellSize)
                        )
                    )
                }

                Box(modifier = modifier) {
                    Text(
                        modifier = Modifier.align(Alignment.Center).clickable {
                            if (dateValue.isNotBlank()) {
                                onDateClicked.invoke(dateValue.toInt())
                            }
                        },
                        fontSize = 16.sp,
                        text = dateValue,
                        color = if (isSelectedDate) selectedColor else textColor
                    )
                }
            }
        }
    }
}
