package zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel

import zlagoda.ukma.edu.ua.db.CustomerCard

sealed class CustomerCardsAction {

    data class OpenEditCustomerCardDialog(val customerCard:  CustomerCard) : CustomerCardsAction()

    object OpenNewCustomerCardDialog : CustomerCardsAction()
    object OpenSearch : CustomerCardsAction()

}