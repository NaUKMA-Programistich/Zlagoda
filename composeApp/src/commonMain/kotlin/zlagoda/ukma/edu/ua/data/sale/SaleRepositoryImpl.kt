package zlagoda.ukma.edu.ua.data.sale

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.MyDatabase
import zlagoda.ukma.edu.ua.db.Sale
import zlagoda.ukma.edu.ua.db.StoreProduct

class SaleRepositoryImpl(
    db: MyDatabase
): SaleRepository {

    private val queries = db.saleQueries

    override suspend fun getSaleByUpcAndChequeNumber(upc: String, chequeNumber: String): Sale? {
        return withContext(Dispatchers.IO) {
            queries.getSaleByUpcAndChequeNumber(upc, chequeNumber).executeAsOneOrNull()
        }
    }

    override fun getAllSales(): Flow<List<Sale>> {
        return queries.getAllSales().asFlow().mapToList(Dispatchers.IO)
    }

    override fun getSalesWithChequeNumber(chequeNumber: String): Flow<List<Sale>> {
        return queries.getSalesWithChequeNumber(chequeNumber).asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun deleteSaleByByUpcAndChequeNumber(upc: String, chequeNumber: String) {
        return withContext(Dispatchers.IO) {
            queries.deleteSaleByUpcAndChequeNumber(upc, chequeNumber)
        }
    }

    override suspend fun insertSale(sale: Sale) {
        return withContext(Dispatchers.IO) {
            queries.insertSale(
                upc = sale.upc,
                chequeNumber = sale.chequeNumber,
                productNumber = sale.productNumber,
                sellingPrice = sale.sellingPrice
            )
        }
    }
}