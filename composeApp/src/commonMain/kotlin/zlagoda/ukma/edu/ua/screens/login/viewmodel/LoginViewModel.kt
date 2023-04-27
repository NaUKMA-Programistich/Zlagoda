package zlagoda.ukma.edu.ua.screens.login.viewmodel

import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.login.LoginRepository
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.utils.authorization.Authorization

class LoginViewModel(
    private val loginRepository: LoginRepository = Injection.loginRepository
): ViewModel<LoginState, LoginAction, LoginEvent>(initialState = LoginState.Loading) {

    init {
        withViewModelScope {
            if(loginRepository.isLogin()) {
                loginRepository.loginInternal()
                setViewAction(LoginAction.GoToMainScreen)
                return@withViewModelScope
            }

            setViewState(LoginState.Auth("", ""))
        }
    }

    override fun obtainEvent(viewEvent: LoginEvent) {
        when (viewEvent) {
            is LoginEvent.SetPassword -> processChangePassword(viewEvent.password)
            is LoginEvent.SetLogin -> processChangeEmail(viewEvent.email)
            is LoginEvent.Login -> processLogin()
        }
    }

    private fun processChangePassword(password: String) {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is LoginState.Auth) {
                setViewState(LoginState.Auth(currentState.login, password))
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
            val state = viewStates().value
            if (state !is LoginState.Auth) return@withViewModelScope

            val login = state.login
            val password = state.password

            setViewState(LoginState.Loading)

            val result = loginRepository.checkEmployee(state.login, state.password)
            result.onSuccess {
                setViewAction(LoginAction.GoToMainScreen)
            }

            result.onFailure {
                setViewState(LoginState.Auth(login, password))
                setViewAction(LoginAction.ShowError(it.message ?: "Unknown error"))
            }
        }
    }
}