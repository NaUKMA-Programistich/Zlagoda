package zlagoda.ukma.edu.ua.screens.employee.viewmodel
import zlagoda.ukma.edu.ua.db.Employee



sealed class EmployeeAction {

    data class OpenEditEmployeeDialog(val employee: Employee) : EmployeeAction()

    object OpenNewEmployeeDialog : EmployeeAction()

}