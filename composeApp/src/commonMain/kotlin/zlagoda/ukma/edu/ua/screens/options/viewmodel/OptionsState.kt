package zlagoda.ukma.edu.ua.screens.options.viewmodel

sealed class OptionsState {
    object EntryDisplay : OptionsState()
    object Loading : OptionsState()
}
