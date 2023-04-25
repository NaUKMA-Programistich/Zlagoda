package zlagoda.ukma.edu.ua.screens.employee_search

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import zlagoda.ukma.edu.ua.screens.employee_search.ui.EmployeeSearchList
import zlagoda.ukma.edu.ua.screens.employee_search.viewmodel.EmployeesSearchEvent
import zlagoda.ukma.edu.ua.screens.employee_search.viewmodel.EmployeesSearchState
import zlagoda.ukma.edu.ua.screens.employee_search.viewmodel.EmployeesSearchViewModel


@Composable
internal fun EmployeeSearchScreen(
    onCloseClick: () -> Unit
) {
    StoredViewModel(factory = { EmployeesSearchViewModel() }) { viewModel ->
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmployeeSearchAppBar {
                onCloseClick()
            }
            when (val state = viewState) {
                is EmployeesSearchState.EmployeeDataList -> {
                    EmployeeSearchList(
                        employeeData = state.data,
                        onSearch = {
                            viewModel.obtainEvent(EmployeesSearchEvent.SearchEmployees(it))
                        }
                    )
                }
                is EmployeesSearchState.Loading -> CircularProgressIndicator()
            }
        }

        when (val action = viewAction) {
            else -> {}
        }
    }
}

@Composable
internal fun EmployeeSearchAppBar(
    onBack: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Search some data by surname of Employee",
            style = MaterialTheme.typography.h5
        )
        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog")
        }
    }
}
