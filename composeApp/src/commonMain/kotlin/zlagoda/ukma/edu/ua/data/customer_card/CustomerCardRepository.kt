package zlagoda.ukma.edu.ua.data.customer_card

import kotlinx.coroutines.flow.Flow
import zlagoda.ukma.edu.ua.db.CustomerCard

interface CustomerCardRepository {

    suspend fun getCustomerCardByCardNumber(cardNumber: String): CustomerCard?

    fun getAllCustomerCards(): Flow<List<CustomerCard>>

    suspend fun deleteCustomerCardByCardNumber(cardNumber: String)

    suspend fun insertCustomerCard(customerCard: CustomerCard)
}