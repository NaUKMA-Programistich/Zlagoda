package zlagoda.ukma.edu.ua.screens.employee_search.viewmodel

import zlagoda.ukma.edu.ua.db.EmployeeSearchData

sealed class EmployeesSearchState {

    object Loading : EmployeesSearchState()

    data class EmployeeDataList(
        val data: List<EmployeeSearchData>,
    ) :EmployeesSearchState()
}