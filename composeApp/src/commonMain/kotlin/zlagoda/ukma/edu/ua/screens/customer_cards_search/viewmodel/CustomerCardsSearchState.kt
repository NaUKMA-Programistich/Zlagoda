package zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel

import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.Product

sealed class CustomerCardsSearchState {

    object Loading : CustomerCardsSearchState()

    data class CustomerCardList(
        val customerCard: List<CustomerCard>,
        val currentEmployee: Employee,
    ) : CustomerCardsSearchState()
}