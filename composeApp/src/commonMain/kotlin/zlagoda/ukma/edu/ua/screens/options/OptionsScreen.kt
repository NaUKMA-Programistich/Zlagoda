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
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.screens.options.ui.OptionsEntryDisplayScreen
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsEvent
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsState
import zlagoda.ukma.edu.ua.screens.options.viewmodel.OptionsViewModel

@Composable
internal fun OptionsScreen() {
    StoredViewModel(factory = { OptionsViewModel() }) { viewModel ->
        val modalController = LocalRootController.current.findModalController()
        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.8f, cornerRadius = 4)
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {
            OptionsState.EntryDisplay -> OptionsEntryDisplayScreen(viewModel::obtainEvent)
            OptionsState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}