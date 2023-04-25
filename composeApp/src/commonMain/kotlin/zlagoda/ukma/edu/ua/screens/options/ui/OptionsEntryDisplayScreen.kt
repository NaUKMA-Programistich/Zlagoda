package zlagoda.ukma.edu.ua.screens.options.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zlagoda.ukma.edu.ua.core.theme.add_button_color
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsEvent

@Composable
internal fun OptionsEntryDisplayScreen(
    onEvent: (OptionsEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = add_button_color),
            onClick = { onEvent(OptionsEvent.LoadReport) }
        ) {
            Text("Load Report")
        }
    }
}