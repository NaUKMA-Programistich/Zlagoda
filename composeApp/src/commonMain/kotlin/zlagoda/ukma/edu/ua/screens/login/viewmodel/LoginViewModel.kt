package zlagoda.ukma.edu.ua.screens.login.viewmodel

import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel

class LoginViewModel: ViewModel<LoginState, LoginAction, LoginEvent>(initialState = LoginState.Auth("","")) {
    override fun obtainEvent(viewEvent: LoginEvent) {
        when (viewEvent) {
            is LoginEvent.SetPassword -> processChangePassword(viewEvent.password)
            is LoginEvent.SetEmail -> processChangeEmail(viewEvent.email)
            is LoginEvent.Login -> processLogin()
        }
    }

    private fun processChangePassword(password: String) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is LoginState.Auth) {
                setViewState(LoginState.Auth(currentState.email, password))
            }
        }
    }

    private fun processChangeEmail(email: String) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is LoginState.Auth) {
                setViewState(LoginState.Auth(email, currentState.password))
            }
        }
    }

    private fun processLogin() {
        withViewModelScope {
            setViewAction(LoginAction.GoToMainScreen)
        }
    }

}