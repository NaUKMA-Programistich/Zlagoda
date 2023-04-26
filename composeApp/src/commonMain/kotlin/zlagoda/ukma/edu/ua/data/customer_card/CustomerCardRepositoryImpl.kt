package zlagoda.ukma.edu.ua.data.customer_card

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.db.MyDatabase
import java.util.*

class CustomerCardRepositoryImpl(
    db: MyDatabase
): CustomerCardRepository {

    private val queries = db.customerCardQueries

    override suspend fun getCustomerCardByCardNumber(cardNumber: String): CustomerCard? {
        return withContext(Dispatchers.IO) {
            queries.getCustomerCardByCardNumber(cardNumber).executeAsOneOrNull()
        }
    }

    override fun getAllCustomerCards(): Flow<List<CustomerCard>> {
        return queries.getAllCustomerCards().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun deleteCustomerCardByCardNumber(cardNumber: String) {
        withContext(Dispatchers.IO) {
            queries.deleteCustomerCardByCardNumber(cardNumber)
        }
    }

    override suspend fun insertCustomerCard(customerCard: CustomerCard) {
        withContext(Dispatchers.IO) {
            queries.insertCustomerCard(
                cardNumber = customerCard.cardNumber.ifBlank { UUID.randomUUID().toString() },
                custSurname = customerCard.custSurname,
                custName = customerCard.custName,
                custPatronymic = customerCard.custPatronymic,
                phoneNumber = customerCard.phoneNumber,
                city = customerCard.city,
                street = customerCard.street,
                zipCode = customerCard.zipCode,
                percent = customerCard.percent,
            )
        }
    }

    override suspend fun filterByPercent(percent: Long): List<CustomerCard> {
        return withContext(Dispatchers.IO) {
            queries.searchByPercent(percent).executeAsList()
        }
    }

    override suspend fun filterBySurname(surname: String): List<CustomerCard> {
        return withContext(Dispatchers.IO) {
            queries.searchBySurname(surname).executeAsList()
        }
    }
}