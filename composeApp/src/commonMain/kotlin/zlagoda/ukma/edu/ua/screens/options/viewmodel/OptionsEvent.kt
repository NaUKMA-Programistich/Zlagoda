package zlagoda.ukma.edu.ua.screens.options.viewmodel

sealed class OptionsEvent {
    object LoadReport : OptionsEvent()
    object Exit : OptionsEvent()


    object DzhosGroup : OptionsEvent()
    data class DzhosNot(val parameter: String) : OptionsEvent()
    object DubovikGroup : OptionsEvent()
    data class DubovikNot(val parameter: String) : OptionsEvent()
    data class MelnykGroup(val parameter: String) : OptionsEvent()
    object MelnykNot : OptionsEvent()
}
