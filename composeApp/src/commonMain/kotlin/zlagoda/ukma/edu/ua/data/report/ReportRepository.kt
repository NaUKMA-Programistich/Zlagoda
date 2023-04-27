package zlagoda.ukma.edu.ua.data.report

import zlagoda.ukma.edu.ua.db.GetSalesSummaryByCategory

interface ReportRepository {

    suspend fun dzhosGetSalesSummaryByCategory(): List<GetSalesSummaryByCategory>
}