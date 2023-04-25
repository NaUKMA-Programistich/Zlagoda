package zlagoda.ukma.edu.ua.data.employee

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.MyDatabase

class EmployeeRepositoryImpl (
    db: MyDatabase
): EmployeeRepository {

    private val queries = db.employeeQueries


    override suspend fun getEmployeeById(id: String): Employee? {
        return withContext(Dispatchers.IO) {
            queries.getEmployeeById(id).executeAsOneOrNull()
        }
    }

    override fun getAllEmployees(): Flow<List<Employee>> {
        return queries.getAllEmployees().asFlow().mapToList(Dispatchers.IO)
    }

    override fun getAllSellers(): Flow<List<Employee>> {
        return queries.getAllSellers().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun deleteEmployeeById(id: String) {
        withContext(Dispatchers.IO) {
            queries.deleteEmployeeById(id)
        }
    }


    override suspend fun insertEmployee(employee: Employee) {
        withContext(Dispatchers.IO) {
            employee.id_of_employee.takeIf { it != "" }?.let { it ->
                queries.insertEmployee(
                    id_of_employee = it,
                    empl_surname = employee.empl_surname,
                    empl_name = employee.empl_name,
                    empl_patronymic = employee.empl_patronymic.takeIf { p -> p?.isNotBlank()?: true},
                    empl_role = employee.empl_role,
                    salary = employee.salary,
                    date_of_birth = employee.date_of_birth,
                    date_of_start = employee.date_of_start,
                    phone_number = employee.phone_number,
                    city = employee.city,
                    street = employee.street,
                    zip_code = employee.zip_code
                )
            }
        }
    }
}