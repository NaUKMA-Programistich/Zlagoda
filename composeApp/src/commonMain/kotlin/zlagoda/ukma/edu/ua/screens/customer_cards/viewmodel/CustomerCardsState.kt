package zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel

import zlagoda.ukma.edu.ua.db.CustomerCard

sealed class CustomerCardsState {

    data class CustomerCardList(val customerCards: List<CustomerCard>) : CustomerCardsState()

    object Loading : CustomerCardsState()
}