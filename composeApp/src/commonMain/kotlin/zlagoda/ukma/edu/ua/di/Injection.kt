package zlagoda.ukma.edu.ua.di

import com.russhwolf.settings.Settings
import zlagoda.ukma.edu.ua.core.db.DriverFactory
import zlagoda.ukma.edu.ua.data.category.CategoryRepositoryImpl
import zlagoda.ukma.edu.ua.data.cheque.ChequeRepositoryImpl
import zlagoda.ukma.edu.ua.data.customer_card.CustomerCardRepositoryImpl
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepositoryImpl
import zlagoda.ukma.edu.ua.data.login.LoginRepositoryImpl
import zlagoda.ukma.edu.ua.data.product.ProductRepositoryImpl
import zlagoda.ukma.edu.ua.data.report.ReportRepositoryImpl
import zlagoda.ukma.edu.ua.data.sale.SaleRepositoryImpl
import zlagoda.ukma.edu.ua.data.store_product.StoreProductRepositoryImpl

import zlagoda.ukma.edu.ua.db.MyDatabase

object Injection {
    private val database = DriverFactory.createDatabase()
    private val settings = Settings()
    val categoryRepository = CategoryRepositoryImpl(database)
    val employeeRepository = EmployeeRepositoryImpl(database)
    val productRepository = ProductRepositoryImpl(database)
    val customerCardRepository = CustomerCardRepositoryImpl(database)
    val chequeCardRepository = ChequeRepositoryImpl(database)
    val saleRepository = SaleRepositoryImpl(database)
    val storeProductRepository = StoreProductRepositoryImpl(database)
    val loginRepository = LoginRepositoryImpl(database, settings)
    val reportRepository = ReportRepositoryImpl(database)
}