package zlagoda.ukma.edu.ua.data.sale

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.Sale

interface SaleRepository {

    suspend fun getSaleByUpcAndChequeNumber(upc:String, chequeNumber: String): Sale?

    fun getAllSales(): Flow<List<Sale>>

    fun getSalesWithChequeNumber(chequeNumber: String):Flow<List<Sale>>

    suspend fun deleteSaleByByUpcAndChequeNumber(upc:String, chequeNumber: String)

    suspend fun insertSale(sale: Sale)
}