package zlagoda.ukma.edu.ua.data.cheque

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.*
import java.util.*

class ChequeRepositoryImpl(
    db: MyDatabase
): ChequeRepository {

    private val queries = db.chequeQueries

    override suspend fun getChequeByChequeNumber(chequeNumber: String): Cheque? {
        return withContext(Dispatchers.IO) {
            queries.getChequeByChequeNumber(chequeNumber).executeAsOneOrNull()
        }
    }

    override fun getChequesData(): Flow<List<GetChequesData>>{
        return queries.getChequesData().asFlow().mapToList(Dispatchers.IO)
    }

    override fun getAllCheques(): Flow<List<Cheque>> {
        return queries.getAllCheques().asFlow().mapToList(Dispatchers.IO)
    }

    override fun getAllChecksInfoBySellerWithProductsInDateRange(idEmployee: String, startDate: String, endDate: String): Flow<List<GetAllChecksInfoBySellerWithProductsInDateRange>>{
        return queries.getAllChecksInfoBySellerWithProductsInDateRange(idEmployee, startDate, endDate).asFlow().mapToList(Dispatchers.IO)
    }

    override fun getAllChecksInfoWithProductsInDateRange(startDate: String, endDate: String): Flow<List<GetAllChecksInfoWithProductsInDateRange>> {
        return queries.getAllChecksInfoWithProductsInDateRange(startDate = startDate, endDate = endDate).asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun getTotalSoldAmountForProduct( idProduct: Long, startDate: String, endDate: String): GetTotalSoldAmountForProduct? {
        return withContext(Dispatchers.IO) {
            queries.getTotalSoldAmountForProduct(idProduct = idProduct, startDate = startDate, endDate = endDate).executeAsOneOrNull()
        }
    }

    override fun getCheckDetailedDescription(checkNumber: String): Flow<List<GetCheckDetailedDescription>> {
        return queries.getCheckDetailedDescription(checkNumber).asFlow().mapToList(Dispatchers.IO)
    }


    override suspend fun deleteChequeByChequeNumber(chequeNumber: String) {
        withContext(Dispatchers.IO) {
            queries.deleteChequeByChequeNumber(chequeNumber)
        }
    }

    override fun getAllChequesBySellerInDateRange(idEmployee: String, startDate: String, endDate: String): Flow<List<Cheque>> {
        return queries.getAllChequesBySellerInDateRange(idEpmloyee = idEmployee, startDate = startDate, endDate = endDate).asFlow().mapToList(Dispatchers.IO)
    }




    override suspend fun getTotalSalesAmountForSellerInDateRange( idEmployee: String, startDate: String, endDate: String) : GetTotalSalesAmountForSellerInDateRange? {
        return withContext(Dispatchers.IO) {
            queries.getTotalSalesAmountForSellerInDateRange(idEmployee, startDate, endDate).executeAsOneOrNull()
        }
    }

    override suspend fun insertCheque(cheque: Cheque) {
        withContext(Dispatchers.IO) {
            queries.insertCheque(
                chequeNumber = cheque.chequeNumber.ifBlank { UUID.randomUUID().toString() },
                idEmployee = cheque.idEmployee,
                cardNumber = cheque.cardNumber,
                printDate = cheque.printDate,
                sumTotal = cheque.sumTotal,
                vat = cheque.vat
            )
        }
    }

    override suspend fun getAllChequesBySellerForToday(idEmployee: String): Flow<List<Cheque>> {
        return queries.getAllChequesBySellerForToday(idEmployee).asFlow().mapToList(Dispatchers.IO)

    }
}