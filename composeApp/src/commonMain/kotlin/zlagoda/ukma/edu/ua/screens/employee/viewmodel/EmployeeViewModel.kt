package zlagoda.ukma.edu.ua.screens.employee.viewmodel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepository
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.screens.login.viewmodel.LoginViewModel
import zlagoda.ukma.edu.ua.screens.products.viewmodel.ProductsAction


class EmployeeViewModel (
    private val repository: EmployeeRepository = Injection.employeeRepository
): ViewModel<EmployeeState, EmployeeAction, EmployeeEvent>(
    initialState = EmployeeState.Loading,
) {

    init {
         getEmployees()
    }

    override fun obtainEvent(viewEvent: EmployeeEvent) {
        when (viewEvent) {
            is EmployeeEvent.SearchEmployeeData -> processSearch()
            is EmployeeEvent.SetAllEmployeeList -> processSetAllEmployeeList()
            is EmployeeEvent.SetSellerList -> processSetSellerList()
            is EmployeeEvent.SetEmployeeList -> processChangeEmployeeList(viewEvent.employeeList)
            is EmployeeEvent.SaveEmployee -> processSaveEmployee(viewEvent.employee)
            is EmployeeEvent.DeleteEmployee -> processDeleteEmployee(viewEvent.employee)
            is EmployeeEvent.EditEmployee -> processEditEmployee(viewEvent.employee)
            is EmployeeEvent.CreateNewEmployee -> processNewEmployee()
            else -> {}
        }
    }

    private fun processSearch() {
        withViewModelScope {
            setViewAction(EmployeeAction.OpenSearchDialog)
        }
    }


    private fun getEmployees() {
        repository.getAllEmployees()
            .onEach { employees ->
                processChangeEmployeeList(employees)
            }.launchIn(viewModelScope)
    }

    private fun getAllSellers() {
        repository.getAllSellers()
            .onEach { employees ->
                processChangeEmployeeList(employees)
            }.launchIn(viewModelScope)
    }
    private fun processSetAllEmployeeList() {
        withViewModelScope {
            setViewState(EmployeeState.Loading)
        }
        getEmployees()
    }


    private fun processSetSellerList() {
        withViewModelScope {
            setViewState(EmployeeState.Loading)
        }
        getAllSellers()
    }

    private fun processChangeEmployeeList(employees: List<Employee>) {
        withViewModelScope {
            setViewState(EmployeeState.EmployeeList(employees))
        }
    }

    private fun processSaveEmployee(employee: Employee) {
        withViewModelScope {
            repository.insertEmployee(employee)
        }
    }

    private fun processDeleteEmployee(employee: Employee) {
        withViewModelScope {
            repository.deleteEmployeeById(employee.id_of_employee)
        }
    }

    private fun processEditEmployee(employee: Employee) {
        withViewModelScope {
            setViewAction(EmployeeAction.OpenEditEmployeeDialog(employee))
        }
    }

    private fun processNewEmployee() {
        withViewModelScope {
            setViewAction(EmployeeAction.OpenNewEmployeeDialog)
        }
    }



}
