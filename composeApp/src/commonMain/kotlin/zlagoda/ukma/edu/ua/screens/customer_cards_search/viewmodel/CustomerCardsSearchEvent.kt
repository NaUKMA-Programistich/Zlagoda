package zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel


sealed class CustomerCardsSearchEvent {
    data class Percent(
        val percent: String
    ) : CustomerCardsSearchEvent()

    data class Surname(
        val surname: String
    ) : CustomerCardsSearchEvent()
}