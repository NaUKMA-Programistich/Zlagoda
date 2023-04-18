package zlagoda.ukma.edu.ua.screens.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginEvent
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginState

@Composable
internal fun LoginViewAuth(state: LoginState.Auth, onEvent: (LoginEvent) -> Unit) {

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Auth",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { email -> onEvent(LoginEvent.SetEmail(email)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { password -> onEvent(LoginEvent.SetPassword(password)) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Button(
            onClick = { onEvent(LoginEvent.Login) },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Login")
        }
    }
}
