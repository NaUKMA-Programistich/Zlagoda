package zlagoda.ukma.edu.ua.screens.employee_search.viewmodel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepository
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.screens.employee_search.viewmodel.EmployeesSearchAction
import zlagoda.ukma.edu.ua.screens.employee_search.viewmodel.EmployeesSearchEvent
import zlagoda.ukma.edu.ua.screens.employee_search.viewmodel.EmployeesSearchState
import zlagoda.ukma.edu.ua.screens.products_search.viewmodel.ProductsSearchState

class EmployeesSearchViewModel(
    private val employeesRepository: EmployeeRepository = Injection.employeeRepository,
): ViewModel<EmployeesSearchState, EmployeesSearchAction, EmployeesSearchEvent>(
    initialState = EmployeesSearchState.Loading,
) {
    init {
       viewModelScope.launch { processNotFilter() }
    }


    override fun obtainEvent(viewEvent: EmployeesSearchEvent) {
        when (viewEvent) {
            is EmployeesSearchEvent.SearchEmployees -> processSearch(viewEvent.employee_surname)
        }
    }

    private suspend fun processNotFilter(){
        setViewState(EmployeesSearchState.EmployeeDataList(emptyList()))
    }

    private fun processSearch(employee_surname: String) {
        withViewModelScope {
            //val state = viewStates().value
            //if (state !is ProductsSearchState.ProductList) return@withViewModelScope

            if (employee_surname.isEmpty()) {
                processNotFilter()
                return@withViewModelScope
            }

            if (employee_surname.isNotEmpty()) {

                employeesRepository.employeeSearchData(employee_surname)
                    .onEach { data ->
                        setViewState(EmployeesSearchState.EmployeeDataList(data))
                    }.launchIn(viewModelScope)
                return@withViewModelScope
            }
        }
    }
}