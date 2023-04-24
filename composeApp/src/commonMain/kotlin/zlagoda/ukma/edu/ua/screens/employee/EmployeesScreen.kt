package zlagoda.ukma.edu.ua.screens.employee

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import zlagoda.ukma.edu.ua.screens.employee.ui.EmployeeItem
import zlagoda.ukma.edu.ua.screens.employee.ui.EmployeeViewList
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeAction
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeState
import zlagoda.ukma.edu.ua.screens.employee.viewmodel.EmployeeViewModel
import zlagoda.ukma.edu.ua.screens.login.ui.LoginViewLoading

@Composable
internal fun EmployeeScreen() {
    StoredViewModel(factory = { EmployeeViewModel() }) { viewModel ->
        val modalController = LocalRootController.current.findModalController()
        val alertConfiguration = AlertConfiguration(maxHeight = 0.7f, maxWidth = 0.5f, cornerRadius = 4)
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        when (val state = viewState) {
            is EmployeeState.EmployeeList -> EmployeeViewList(
                state = state,
                onEvent = { viewModel.obtainEvent(it) }
            )
            is EmployeeState.Loading -> {
                LoginViewLoading()
            }
        }

        when (val action = viewAction) {
            is EmployeeAction.OpenEditEmployeeDialog -> modalController.present(alertConfiguration) { key ->
                EmployeeItem(
                    employee = action.employee,
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            is EmployeeAction.OpenNewEmployeeDialog -> modalController.present(alertConfiguration) { key ->
                EmployeeItem(
                    onCloseClick = { modalController.popBackStack(key) },
                    onEvent = { viewModel.obtainEvent(it) }
                )
            }
            null -> {}
        }
    }
}
