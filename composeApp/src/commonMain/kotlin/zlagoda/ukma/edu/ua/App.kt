package zlagoda.ukma.edu.ua

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import zlagoda.ukma.edu.ua.core.theme.AppTheme
import zlagoda.ukma.edu.ua.navigation.NavigationGraph

@Composable
internal fun App() = AppTheme {
    val configuration = remember { OdysseyConfiguration() }

    setNavigationContent(configuration) {
        NavigationGraph()
    }
}