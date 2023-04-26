package zlagoda.ukma.edu.ua.screens.options

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.navigation.NavigationRoute
import zlagoda.ukma.edu.ua.screens.options.ui.OptionsEntryDisplayScreen
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsAction
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsState
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsViewModel

@Composable
internal fun OptionsScreen() {
    StoredViewModel(factory = { OptionsViewModel() }) { viewModel ->
        val navController = LocalRootController.current
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {
            is OptionsState.EntryDisplay -> OptionsEntryDisplayScreen(state, viewModel::obtainEvent)
            OptionsState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        when (viewAction) {
            OptionsAction.GoToLogin -> {
                navController.findRootController().push(NavigationRoute.Login.name)
            }
            null -> {}
        }
    }
}