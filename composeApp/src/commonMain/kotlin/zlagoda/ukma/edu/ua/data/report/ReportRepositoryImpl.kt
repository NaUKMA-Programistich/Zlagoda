package zlagoda.ukma.edu.ua.data.report

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
}