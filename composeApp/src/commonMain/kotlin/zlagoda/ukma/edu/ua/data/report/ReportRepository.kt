package zlagoda.ukma.edu.ua.data.report

import zlagoda.ukma.edu.ua.db.*

interface ReportRepository {

    suspend fun dzhosGetSalesSummaryByCategory(): List<GetSalesSummaryByCategory>
    suspend fun dzhosGetProductsNotSoldAndNotPromotionalAndPriceGreatThan(sellPrice: Double): List<GetProductsNotSoldAndNotPromotionalAndPriceGreatThan>

    suspend fun dubovikGetSalesInfoForAllEmployees(): List<GetSalesInfoForAllEmployees>

    suspend fun dubovikGetEmployeesThatGetSalesOnlyForCustomerWithSpecificSurname(surname: String): List<Employee>

    suspend fun melnykGetListSellersRankAndTheirTopCategoriesRank(filterSellingPrice: Double?) : List<GetListSellersRankAndTheirTopCategoriesRank>

    suspend fun melnykGetLowNotPopularCategoriesExceptCategoriesWithProductGaming(): List<String>

}