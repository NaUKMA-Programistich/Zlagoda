package zlagoda.ukma.edu.ua.data.cheque

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.GetCheckDetailedDescription


interface ChequeRepository {

    suspend fun getChequeByChequeNumber(chequeNumber: String): Cheque?

    fun getAllCheques(): Flow<List<Cheque>>

    fun getCheckDetailedDescription(checkNumber: String): Flow<List<GetCheckDetailedDescription>>
    suspend fun deleteChequeByChequeNumber(chequeNumber: String)

    suspend fun insertCheque(cheque: Cheque)
}