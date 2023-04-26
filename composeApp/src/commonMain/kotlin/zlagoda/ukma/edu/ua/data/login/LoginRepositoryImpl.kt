package zlagoda.ukma.edu.ua.data.login

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import zlagoda.ukma.edu.ua.core.ktx.encrypt
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.MyDatabase

class LoginRepositoryImpl(
    db: MyDatabase,
    private val settings: Settings,
) : LoginRepository {
    private val queries = db.employeeQueries

    private val currentUserFlow = MutableStateFlow<Employee?>(null)

    override fun getCurrentEmployee(): StateFlow<Employee?> {
        return currentUserFlow.asStateFlow()
    }

    override fun isLogin(): Boolean {
        return settings.get<String>("login") != null && settings.get<String>("password") != null
    }

    override fun logout() {
        settings.clear()
    }

    override suspend fun checkEmployee(login: String, password: String): Result<Unit> {
        if (login.isEmpty()) {
            return Result.failure(Exception("Login is empty"))
        }

        if (password.isEmpty()) {
            return Result.failure(Exception("Password is empty"))
        }

        val searchEmployer = queries
            .findEmployeeByLogin(login)
            .executeAsOneOrNull()
            ?: return Result.failure(Exception("Employee not found"))

        if (searchEmployer.password != password.encrypt()) {
            return Result.failure(Exception("Wrong password"))
        }

        settings["login"] = login
        settings["password"] = login

        currentUserFlow.emit(searchEmployer)
        return Result.success(Unit)
    }

    override suspend fun loginInternal(login: String?) {
        if (login == null) {
            val loginInternal = settings.get<String>("login") ?: return

            val searchEmployer = queries
                .findEmployeeByLogin(loginInternal)
                .executeAsOneOrNull() ?: return

            currentUserFlow.emit(searchEmployer)
            return
        }

        settings["login"] = login

        val searchEmployer = queries
            .findEmployeeByLogin(login)
            .executeAsOneOrNull() ?: return

        currentUserFlow.emit(searchEmployer)
    }
}