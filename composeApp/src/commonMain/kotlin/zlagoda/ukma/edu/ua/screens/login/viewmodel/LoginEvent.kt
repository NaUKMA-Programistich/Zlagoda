package zlagoda.ukma.edu.ua.screens.login.viewmodel

sealed class LoginEvent {
    data class SetPassword(val password: String) : LoginEvent()
    data class SetEmail(val email: String) : LoginEvent()

    object Login : LoginEvent()
}
