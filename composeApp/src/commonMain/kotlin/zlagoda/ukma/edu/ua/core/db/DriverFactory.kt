package zlagoda.ukma.edu.ua.core.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import zlagoda.ukma.edu.ua.db.MyDatabase
import java.io.File

class DriverFactory {
    companion object {
        fun createDriver(): SqlDriver {
            val dbFilePath = "database.db"
            val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$dbFilePath")
            if (!File(dbFilePath).exists()) {
                MyDatabase.Schema.create(driver)
            }
            // val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            // MyDatabase.Schema.create(driver)
            return driver
        }
    }
}
