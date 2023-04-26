package zlagoda.ukma.edu.ua

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import zlagoda.ukma.edu.ua.core.theme.AppTheme
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.navigation.NavigationGraph
import kotlin.system.exitProcess

@Composable
internal fun FrameWindowScope.App() = AppTheme {
    val loginRepository = remember { Injection.loginRepository }

    val backgroundColor = MaterialTheme.colors.background
    val configuration = remember { OdysseyConfiguration(backgroundColor = backgroundColor) }

    setNavigationContent(configuration) {
        NavigationGraph()
    }

    MenuBar {
        Menu("Actions") {
            Item("Force Logout", onClick = {
                loginRepository.logout()
                exitProcess(0)
            })
        }
    }
}