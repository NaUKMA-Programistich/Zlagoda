package zlagoda.ukma.edu.ua.data.cheque

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.Cheque

interface ChequeRepository {

    suspend fun getChequeByChequeNumber(chequeNumber: String): Cheque?

    fun getAllCheques(): Flow<List<Cheque>>

    suspend fun deleteChequeByChequeNumber(chequeNumber: String)

    suspend fun insertCheque(cheque: Cheque)
}