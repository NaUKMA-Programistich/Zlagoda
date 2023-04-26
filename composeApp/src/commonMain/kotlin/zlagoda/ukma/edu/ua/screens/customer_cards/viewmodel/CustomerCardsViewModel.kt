package zlagoda.ukma.edu.ua.screens.customer_cards.viewmodel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlagoda.ukma.edu.ua.core.ktx.isManager
import zlagoda.ukma.edu.ua.core.ktx.isSeller
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.customer_card.CustomerCardRepository
import zlagoda.ukma.edu.ua.data.login.LoginRepository
import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.di.Injection

class CustomerCardViewModel(
    private val repository: CustomerCardRepository = Injection.customerCardRepository,
    private val loginRepository: LoginRepository = Injection.loginRepository
): ViewModel<CustomerCardsState, CustomerCardsAction, CustomerCardsEvent>(
    initialState = CustomerCardsState.Loading,
) {
    init { getCustomerCards() }

    override fun obtainEvent(viewEvent: CustomerCardsEvent) {
        when (viewEvent) {
            is CustomerCardsEvent.SetCustomerCardList -> processChangeCustomerCardList(viewEvent.customerCardList)
            is CustomerCardsEvent.SaveCustomerCard -> processSaveCustomerCard(viewEvent.customerCard)
            is CustomerCardsEvent.DeleteCustomerCard -> processDeleteCustomerCard(viewEvent.customerCard)
            is CustomerCardsEvent.EditCustomerCard -> processEditCustomerCard(viewEvent.customerCard)
            is CustomerCardsEvent.CreateNewCustomerCard -> processNewCustomerCard()
            CustomerCardsEvent.Search -> processSearch()
        }
    }

    private fun processSearch() {
        withViewModelScope {
            setViewAction(CustomerCardsAction.OpenSearch)
        }
    }

    private fun getCustomerCards() {
        repository.getAllCustomerCards()
            .onEach { customerCards ->
                processChangeCustomerCardList(customerCards)
            }.launchIn(viewModelScope)
    }

    private fun processChangeCustomerCardList(customerCardList: List<CustomerCard>) {
        withViewModelScope {
            setViewState(CustomerCardsState.CustomerCardList(customerCardList))
        }
    }

    private fun processSaveCustomerCard(customerCard: CustomerCard) {
        withViewModelScope {
            repository.insertCustomerCard(customerCard)
        }
    }

    private fun processDeleteCustomerCard(customerCard: CustomerCard) {
        withViewModelScope {
            repository.deleteCustomerCardByCardNumber(customerCard.cardNumber)
        }
    }

    private fun processEditCustomerCard(customerCard: CustomerCard) {
        withViewModelScope {
            setViewAction(CustomerCardsAction.OpenEditCustomerCardDialog(customerCard))
        }
    }

    private fun processNewCustomerCard() {
        withViewModelScope {
            setViewAction(CustomerCardsAction.OpenNewCustomerCardDialog)
        }
    }
}