package zlagoda.ukma.edu.ua.di

import zlagoda.ukma.edu.ua.core.db.DriverFactory
import zlagoda.ukma.edu.ua.data.category.CategoryRepositoryImpl
import zlagoda.ukma.edu.ua.data.cheque.ChequeRepositoryImpl
import zlagoda.ukma.edu.ua.data.customer_card.CustomerCardRepositoryImpl
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepositoryImpl
import zlagoda.ukma.edu.ua.data.product.ProductRepositoryImpl
import zlagoda.ukma.edu.ua.data.sale.SaleRepositoryImpl
import zlagoda.ukma.edu.ua.data.store_product.StoreProductRepositoryImpl

import zlagoda.ukma.edu.ua.db.MyDatabase

object Injection {
    private val database = MyDatabase(DriverFactory.createDriver())
    val categoryRepository = CategoryRepositoryImpl(database)
    val employeeRepository = EmployeeRepositoryImpl(database)
    val productRepository = ProductRepositoryImpl(database)
    val customerCardRepository = CustomerCardRepositoryImpl(database)
    val chequeCardRepository = ChequeRepositoryImpl(database)
    val saleRepository = SaleRepositoryImpl(database)
    val storeProductRepository = StoreProductRepositoryImpl(database)
}