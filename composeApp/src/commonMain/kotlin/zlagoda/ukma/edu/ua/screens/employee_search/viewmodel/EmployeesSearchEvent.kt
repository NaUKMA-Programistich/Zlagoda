package zlagoda.ukma.edu.ua.screens.employee_search.viewmodel

sealed class EmployeesSearchEvent {

    data class SearchEmployees(val employee_surname: String) : EmployeesSearchEvent()

}