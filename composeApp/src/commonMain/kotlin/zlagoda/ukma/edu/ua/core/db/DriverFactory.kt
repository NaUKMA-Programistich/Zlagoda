package zlagoda.ukma.edu.ua.core.db

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import zlagoda.ukma.edu.ua.core.ktx.toDate
import zlagoda.ukma.edu.ua.core.ktx.toStr
import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.MyDatabase
import java.io.File
import java.util.Date

object DriverFactory {
    private fun createDriver(): SqlDriver {
        val dbFilePath = "database.db"
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$dbFilePath")
        if (!File(dbFilePath).exists()) {
            MyDatabase.Schema.create(driver)
        }
        return driver
    }

    fun createDatabase(): MyDatabase {
        return MyDatabase(
            driver = createDriver(),
            chequeAdapter = Cheque.Adapter(
                printDateAdapter = dateAdapter
            ),
            employeeAdapter = Employee.Adapter(
                date_of_birthAdapter = dateAdapter,
                date_of_startAdapter = dateAdapter
            ),
        )
    }

    private val dateAdapter = object : ColumnAdapter<Date, String> {
        override fun decode(databaseValue: String) = databaseValue.toDate()!!
        override fun encode(value: Date) = value.toStr()
    }
}
