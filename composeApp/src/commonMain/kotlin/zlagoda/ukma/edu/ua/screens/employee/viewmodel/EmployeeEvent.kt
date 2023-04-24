package zlagoda.ukma.edu.ua.screens.employee.viewmodel
import zlagoda.ukma.edu.ua.db.Employee


sealed class EmployeeEvent {

    data class SetEmployeeList(val employeeList: List<Employee>) : EmployeeEvent()

    data class DeleteEmployee(val employee: Employee) : EmployeeEvent()

    data class SaveEmployee(val employee: Employee) : EmployeeEvent()

    data class EditEmployee(val employee: Employee) : EmployeeEvent()

    object CreateNewEmployee: EmployeeEvent()
}