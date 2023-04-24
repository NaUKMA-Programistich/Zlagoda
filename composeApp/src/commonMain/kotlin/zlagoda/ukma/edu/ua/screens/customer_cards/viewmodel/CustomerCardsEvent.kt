package zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel

import zlagoda.ukma.edu.ua.db.CustomerCard

sealed class CustomerCardsEvent {

    data class SetCustomerCardList(val customerCardList: List<CustomerCard>) : CustomerCardsEvent()

    data class DeleteCustomerCard(val customerCard: CustomerCard) : CustomerCardsEvent()

    data class SaveCustomerCard(val customerCard: CustomerCard) : CustomerCardsEvent()

    data class EditCustomerCard(val customerCard: CustomerCard) : CustomerCardsEvent()

    object CreateNewCustomerCard: CustomerCardsEvent()
}