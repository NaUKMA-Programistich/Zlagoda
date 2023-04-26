package zlagoda.ukma.edu.ua.data.login

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.Employee

interface LoginRepository {

    fun getCurrentEmployee(): Flow<Employee?>

    fun isLogin(): Boolean

    fun logout()

    suspend fun checkEmployee(login: String, password: String): Result<Unit>
    suspend fun loginInternal()
}