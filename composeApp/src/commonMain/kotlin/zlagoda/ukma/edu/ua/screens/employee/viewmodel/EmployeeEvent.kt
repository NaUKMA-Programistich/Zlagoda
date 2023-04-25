package zlagoda.ukma.edu.ua.screens.employee.viewmodel
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.screens.products.viewmodel.ProductsEvent


sealed class EmployeeEvent {

    data class SetEmployeeList(val employeeList: List<Employee>) : EmployeeEvent()

    object SetAllEmployeeList : EmployeeEvent()
    object SetSellerList : EmployeeEvent()

    object SearchEmployeeData: EmployeeEvent()
    data class DeleteEmployee(val employee: Employee) : EmployeeEvent()

    data class SaveEmployee(val employee: Employee) : EmployeeEvent()

    data class EditEmployee(val employee: Employee) : EmployeeEvent()

    object CreateNewEmployee: EmployeeEvent()
}