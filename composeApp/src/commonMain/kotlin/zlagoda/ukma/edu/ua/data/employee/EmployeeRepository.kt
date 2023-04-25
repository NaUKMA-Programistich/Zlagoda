package zlagoda.ukma.edu.ua.data.employee

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.EmployeeSearchData


interface EmployeeRepository {

    suspend fun getEmployeeById(id: String): Employee?

    fun getAllEmployees(): Flow<List<Employee>>

    fun employeeSearchData(employee_surname : String): Flow<List<EmployeeSearchData>>
    fun getAllSellers(): Flow<List<Employee>>

    suspend fun deleteEmployeeById(id: String)

    suspend fun insertEmployee(employee: Employee)

}