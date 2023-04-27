package zlagoda.ukma.edu.ua.data.report

import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.GetProductsNotSoldAndNotPromotionalAndPriceGreatThan
import zlagoda.ukma.edu.ua.db.GetSalesInfoForAllEmployees
import zlagoda.ukma.edu.ua.db.GetSalesSummaryByCategory

interface ReportRepository {

    suspend fun dzhosGetSalesSummaryByCategory(): List<GetSalesSummaryByCategory>
    suspend fun dzhosGetProductsNotSoldAndNotPromotionalAndPriceGreatThan(sellPrice: Double): List<GetProductsNotSoldAndNotPromotionalAndPriceGreatThan>

    suspend fun dubovikGetSalesInfoForAllEmployees(): List<GetSalesInfoForAllEmployees>

    suspend fun dubovikGetEmployeesThatGetSalesOnlyForCustomerWithSpecificSurname(surname: String): List<Employee>
}