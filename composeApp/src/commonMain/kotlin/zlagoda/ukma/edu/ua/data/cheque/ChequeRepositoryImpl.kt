package zlagoda.ukma.edu.ua.data.cheque

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.MyDatabase
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

    override fun getAllCheques(): Flow<List<Cheque>> {
        return queries.getAllCheques().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun deleteChequeByChequeNumber(chequeNumber: String) {
        withContext(Dispatchers.IO) {
            queries.deleteChequeByChequeNumber(chequeNumber)
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
}