package zlagoda.ukma.edu.ua.data.employee

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.Employee


interface EmployeeRepository {

    suspend fun getEmployeeById(id: String): Employee?

    fun getAllEmployees(): Flow<List<Employee>>

    suspend fun deleteEmployeeById(id: String)

    suspend fun insertEmployee(employee: Employee)

}