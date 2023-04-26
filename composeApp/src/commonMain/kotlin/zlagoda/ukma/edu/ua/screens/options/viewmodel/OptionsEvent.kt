package zlagoda.ukma.edu.ua.screens.options.viewmodel

sealed class OptionsEvent {
    object LoadReport : OptionsEvent()
    object Exit : OptionsEvent()
}
