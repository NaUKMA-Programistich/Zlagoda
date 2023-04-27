package zlagoda.ukma.edu.ua.data.report

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.GetProductsNotSoldAndNotPromotionalAndPriceGreatThan
import zlagoda.ukma.edu.ua.db.GetSalesInfoForAllEmployees
import zlagoda.ukma.edu.ua.db.GetSalesSummaryByCategory
import zlagoda.ukma.edu.ua.db.MyDatabase

class ReportRepositoryImpl(
    database: MyDatabase
): ReportRepository {

    private val dzhosQueries = database.dzhosQueries
    private val dubovikQueries = database.dubovikQueries
    private val melnikQueries = database.melnikQueries

    override suspend fun dzhosGetSalesSummaryByCategory(): List<GetSalesSummaryByCategory> {
        return withContext(Dispatchers.IO) {
            dzhosQueries.getSalesSummaryByCategory().executeAsList()
        }
    }

    override suspend fun dzhosGetProductsNotSoldAndNotPromotionalAndPriceGreatThan(sellPrice: Double): List<GetProductsNotSoldAndNotPromotionalAndPriceGreatThan> {
        return withContext(Dispatchers.IO) {
            dzhosQueries.getProductsNotSoldAndNotPromotionalAndPriceGreatThan(sellPrice).executeAsList()
        }
    }

    override suspend fun dubovikGetSalesInfoForAllEmployees(): List<GetSalesInfoForAllEmployees> {
        return withContext(Dispatchers.IO) {
            dubovikQueries.getSalesInfoForAllEmployees().executeAsList()
        }
    }

    override suspend fun dubovikGetEmployeesThatGetSalesOnlyForCustomerWithSpecificSurname(surname: String): List<Employee> {
        return withContext(Dispatchers.IO) {
            dubovikQueries.getEmployeesThatGetSalesOnlyForCustomerWithSpecificSurname(surname).executeAsList()
        }
    }

}