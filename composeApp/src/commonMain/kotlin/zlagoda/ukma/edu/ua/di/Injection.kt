package zlagoda.ukma.edu.ua.di

import zlagoda.ukma.edu.ua.core.db.DriverFactory
import zlagoda.ukma.edu.ua.data.category.CategoryRepositoryImpl
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepositoryImpl
import zlagoda.ukma.edu.ua.data.product.ProductRepositoryImpl

import zlagoda.ukma.edu.ua.db.MyDatabase

object Injection {
    private val database = MyDatabase(DriverFactory.createDriver())
    val categoryRepository = CategoryRepositoryImpl(database)
    val employeeRepository = EmployeeRepositoryImpl(database)
    val productRepository = ProductRepositoryImpl(database)
}