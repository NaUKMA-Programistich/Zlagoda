import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import zlagoda.ukma.edu.ua.App

fun main() = application {
    Window(
        title = "Zlagoda",
        state = rememberWindowState(width = 1000.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) { App() }
}
