package zlagoda.ukma.edu.ua.screens.customer_cards_search.viewmodel

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.data.customer_card.CustomerCardRepository
import zlagoda.ukma.edu.ua.data.login.LoginRepository
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.di.Injection

class CustomerCardsSearchViewModel(
    private val loginRepository: LoginRepository = Injection.loginRepository,
    private val customerCardRepository: CustomerCardRepository = Injection.customerCardRepository,
): ViewModel<CustomerCardsSearchState, CustomerCardsSearchAction, CustomerCardsSearchEvent>(
    initialState = CustomerCardsSearchState.Loading,
) {
    init {
        viewModelScope.launch { processNotFilter() }
    }

    override fun obtainEvent(viewEvent: CustomerCardsSearchEvent) {
        when(viewEvent) {
            is CustomerCardsSearchEvent.Percent -> processPercent(viewEvent.percent)
            is CustomerCardsSearchEvent.Surname -> processSurname(viewEvent.surname)
        }
    }

    private fun processSurname(surname: String) {
        withViewModelScope {
            val state = viewStates().value
            if (state !is CustomerCardsSearchState.CustomerCardList) return@withViewModelScope
            val employee = state.currentEmployee

            if (surname.isEmpty()) {
                processNotFilter()
                return@withViewModelScope
            }

            val filter = customerCardRepository.filterBySurname(surname)
            setViewState(CustomerCardsSearchState.CustomerCardList(filter, employee))
        }
    }

    private fun processPercent(percent: String) {
        withViewModelScope {
            val state = viewStates().value
            if (state !is CustomerCardsSearchState.CustomerCardList) return@withViewModelScope
            val employee = state.currentEmployee

            if (percent.isEmpty()) {
                processNotFilter()
                return@withViewModelScope
            }

            val percentString = percent.toLongOrNull()
            if (percentString == null) {
                setViewAction(CustomerCardsSearchAction.ShowError("Percent must be a number"))
                return@withViewModelScope
            }

            val filter = customerCardRepository.filterByPercent(percentString)
            setViewState(CustomerCardsSearchState.CustomerCardList(filter, employee))
        }
    }

    private suspend fun processNotFilter() {
        customerCardRepository.getAllCustomerCards().collectLatest { cards ->
            loginRepository.getCurrentEmployee().collectLatest { employee ->
                if (employee == null) return@collectLatest
                setViewState(CustomerCardsSearchState.CustomerCardList(cards, employee))
            }
        }
    }
}