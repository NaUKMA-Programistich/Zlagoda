package zlagoda.ukma.edu.ua.screens.employee.viewmodel
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.screens.products.viewmodel.ProductsAction


sealed class EmployeeAction {

    data class OpenEditEmployeeDialog(val employee: Employee) : EmployeeAction()
    object OpenSearchDialog: EmployeeAction()
    object OpenNewEmployeeDialog : EmployeeAction()



}