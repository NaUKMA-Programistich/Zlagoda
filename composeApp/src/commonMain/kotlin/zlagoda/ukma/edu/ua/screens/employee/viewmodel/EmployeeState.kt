package zlagoda.ukma.edu.ua.screens.employee.viewmodel

import zlagoda.ukma.edu.ua.db.Employee

sealed class EmployeeState {

    data class EmployeeList(val employees: List<Employee>) : EmployeeState()

    object Loading : EmployeeState()
}