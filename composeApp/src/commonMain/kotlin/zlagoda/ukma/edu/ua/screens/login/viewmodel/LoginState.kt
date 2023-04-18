package zlagoda.ukma.edu.ua.screens.login.viewmodel

sealed class LoginState {
    object Loading : LoginState()
    data class Auth(val email: String, val password: String) : LoginState()
}
