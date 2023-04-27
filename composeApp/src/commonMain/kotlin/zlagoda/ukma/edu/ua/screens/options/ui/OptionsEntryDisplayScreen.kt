package zlagoda.ukma.edu.ua.screens.options.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.core.theme.add_button_color
import zlagoda.ukma.edu.ua.screens.options.ui.dzhos.ComposableReportDzhos
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsEvent
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsState

@Composable
internal fun OptionsEntryDisplayScreen(
    state: OptionsState.EntryDisplay,
    onEvent: (OptionsEvent) -> Unit
) {

    Row {
        Column(
            modifier = Modifier.fillMaxHeight().weight(4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ComposableReportDzhos(onEvent)
            ComposableReportDzhos(onEvent)
            ComposableReportDzhos(onEvent)
        }
        ComposableOptionsData(state, onEvent)
    }
}

@Composable
private fun RowScope.ComposableOptionsData(
    state: OptionsState.EntryDisplay,
    onEvent: (OptionsEvent) -> Unit
) {
    val text = """
        Current Employer: 
        Name: "${state.employee.empl_name}"
        SurName: "${state.employee.empl_surname}"
        Login: "${state.employee.login}"
        Role: "${state.employee.empl_role}"
    """.trimIndent()

    Column(
        modifier = Modifier.fillMaxHeight().weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
            onClick = { onEvent(OptionsEvent.LoadReport) }
        ) {
            Text("Load Report")
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
            onClick = { onEvent(OptionsEvent.Exit) }
        ) {
            Text("Exit")
        }
    }
}