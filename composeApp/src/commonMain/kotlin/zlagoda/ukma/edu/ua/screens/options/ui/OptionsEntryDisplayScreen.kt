package zlagoda.ukma.edu.ua.screens.options.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.core.theme.add_button_color
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsEvent
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsState

@Composable
internal fun OptionsEntryDisplayScreen(
    state: OptionsState.EntryDisplay,
    onEvent: (OptionsEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Current Employer: \"${state.employee.login}\" with role \"${state.employee.empl_role}\"")
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