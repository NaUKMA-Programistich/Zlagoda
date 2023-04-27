package zlagoda.ukma.edu.ua.data.cheque

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.*


interface ChequeRepository {

    suspend fun getChequeByChequeNumber(chequeNumber: String): Cheque?

    fun getAllCheques(): Flow<List<Cheque>>

    fun getCheckDetailedDescription(checkNumber: String): Flow<List<GetCheckDetailedDescription>>

    fun getChequesData(): Flow<List<GetChequesData>>

    fun getAllChecksInfoBySellerWithProductsInDateRange(idEmployee: String, startDate: String, endDate: String): Flow<List<GetAllChecksInfoBySellerWithProductsInDateRange>>

    fun getAllChecksInfoWithProductsInDateRange(startDate: String, endDate: String): Flow<List<GetAllChecksInfoWithProductsInDateRange>>
    suspend fun getTotalSoldAmountForProduct(idProduct: Long, startDate: String, endDate: String): GetTotalSoldAmountForProduct?
    suspend fun deleteChequeByChequeNumber(chequeNumber: String)

    suspend fun insertCheque(cheque: Cheque)

    suspend fun getAllChequesBySellerForToday(idEmployee: String): Flow<List<Cheque>>

}



/*


-- Менеджер: Визначити загальну суму проданих товарів з чеків,
-- створених певним касиром за певний період часу;
getTotalSalesAmountForSellerInDateRange:
SELECT SUM(cheque.sumTotal) AS totalSalesSum
FROM cheque
WHERE cheque.idEmployee = :idEmployee
AND date(cheque.printDate) BETWEEN date(:startDate) AND date(:endDate);



-- Касир: Переглянути список усіх чеків, що створив касир за цей день
getAllChequesBySellerForToday:
SELECT cheque.*
FROM cheque
INNER JOIN employee ON cheque.idEmployee = employee.id_of_employee
WHERE employee.id_of_employee = :idEmloyee
AND datetime(cheque.printDate) BETWEEN datetime('now', 'start of day') AND datetime('now', 'localtime');

-- Касир: Переглянути список усіх чеків, що створив касир за певний період часу
getAllChequesBySellerInDateRange:
SELECT cheque.*
FROM cheque
INNER JOIN employee ON cheque.idEmployee = employee.id_of_employee
WHERE employee.id_of_employee = :idEmloyee
AND date(cheque.printDate) BETWEEN date(:startDate) AND date(:endDate);
*/
