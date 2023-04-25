package zlagoda.ukma.edu.ua.screens.login.viewmodel

import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.db.Employee

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
    
    companion object{
        val user : Employee = Employee("28af1c8c-e136-11ed-b5ea-0242aa120002", "Admin","Admin",null,"Manager",10000.00,"1998-01-03","2022-12-07","+1234567890","London","Baker","123");

        fun isSeller(): Boolean = user.empl_role == "Seller"
        fun isManager(): Boolean = user.empl_role == "Manager"
    }

}