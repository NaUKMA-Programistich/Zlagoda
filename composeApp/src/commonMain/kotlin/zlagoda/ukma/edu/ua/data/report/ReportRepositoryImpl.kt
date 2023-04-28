package zlagoda.ukma.edu.ua.data.report

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.*

class ReportRepositoryImpl(
    database: MyDatabase
): ReportRepository {

    private val dzhosQueries = database.dzhosQueries
    private val dubovikQueries = database.dubovikQueries
    private val melnykQueries = database.melnykQueries

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

    override suspend fun melnykGetListSellersRankAndTheirTopCategoriesRank(filterSellingPrice: Double?) : List<GetListSellersRankAndTheirTopCategoriesRank> {
        return withContext(Dispatchers.IO) {
            melnykQueries.getListSellersRankAndTheirTopCategoriesRank(filterSellingPrice).executeAsList()
        }
    }

    override suspend fun melnykGetLowNotPopularCategoriesExceptCategoriesWithProductGaming() : List<String> {
        return withContext(Dispatchers.IO) {
            melnykQueries.getLowNotPopularCategoriesExceptCategoriesWithProductGaming().executeAsList()
        }
    }

}