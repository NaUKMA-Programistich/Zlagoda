package zlagoda.ukma.edu.ua.screens.options.viewmodel

sealed class OptionsEvent {
    object LoadReport : OptionsEvent()
    object Exit : OptionsEvent()

    object DzhosGroup : OptionsEvent()
    object DzhosNot : OptionsEvent()
    object DubovikGroup : OptionsEvent()
    data class DubovikNot(val parameter: String) : OptionsEvent()
    object MelnykGroup : OptionsEvent()
    object MelnykNot : OptionsEvent()
}
