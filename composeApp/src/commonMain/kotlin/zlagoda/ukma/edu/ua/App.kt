package zlagoda.ukma.edu.ua

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.adeo.kviewmodel.odyssey.setupWithViewModels
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import zlagoda.ukma.edu.ua.navigation.NavigationGraph

@Composable
internal fun App() = AppTheme {
    val configuration = remember { OdysseyConfiguration() }

    setNavigationContent(configuration) {
        NavigationGraph()
        // LocalRootController.current.setupWithViewModels()
    }
}