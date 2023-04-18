package zlagoda.ukma.edu.ua.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import zlagoda.ukma.edu.ua.navigation.NavigationRoute
import zlagoda.ukma.edu.ua.screens.login.ui.LoginViewAuth
import zlagoda.ukma.edu.ua.screens.login.ui.LoginViewLoading
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginAction
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginState
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginViewModel

@Composable
internal fun LoginScreen() {
    StoredViewModel(factory = { LoginViewModel() }) { viewModel ->
        val navController = LocalRootController.current
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {
            is LoginState.Loading -> LoginViewLoading()
            is LoginState.Auth -> LoginViewAuth(
                state = state,
                onEvent = { viewModel.obtainEvent(it) }
            )
        }

        when (viewAction) {
            LoginAction.GoToMainScreen -> {
                navController.push(NavigationRoute.Main.name)
            }
            null -> {}
        }
    }
}
