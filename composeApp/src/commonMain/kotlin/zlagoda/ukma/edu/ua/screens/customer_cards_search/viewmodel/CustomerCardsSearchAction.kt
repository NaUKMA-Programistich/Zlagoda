package zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel

sealed class CustomerCardsSearchAction {
    data class ShowError(val message: String): CustomerCardsSearchAction()
}
