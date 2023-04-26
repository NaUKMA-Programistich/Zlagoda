package zlagoda.ukma.edu.ua.screens.login.viewmodel

sealed class LoginAction {
    object GoToMainScreen : LoginAction()

    data class ShowError(val message: String) : LoginAction()
}
