package zlagoda.ukma.edu.ua.screens.order.viewmodel
import kotlinx.coroutines.flow.collectLatest
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.cheque.ChequeRepository
import zlagoda.ukma.edu.ua.data.customer_card.CustomerCardRepository
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepository
import zlagoda.ukma.edu.ua.data.login.LoginRepository
import zlagoda.ukma.edu.ua.data.sale.SaleRepository
import zlagoda.ukma.edu.ua.data.store_product.StoreProductRepository
import zlagoda.ukma.edu.ua.db.*
import zlagoda.ukma.edu.ua.di.Injection


class OrderViewModel(
    private val repository: ChequeRepository = Injection.chequeCardRepository,
    private val customerCardRepository: CustomerCardRepository = Injection.customerCardRepository,
    private val employeeRepository: EmployeeRepository = Injection.employeeRepository,

    private val loginRepository: LoginRepository = Injection.loginRepository,
    private val saleRepository: SaleRepository = Injection.saleRepository,
    private val storeProductRepository: StoreProductRepository = Injection.storeProductRepository
): ViewModel<OrderState, OrderAction, OrderEvent>(
    initialState = OrderState.Loading,
) {
    init {
        withViewModelScope {
            customerCardRepository.getAllCustomerCards().collectLatest { customerCards ->
                val customerCardMap: Map<String, String> = customerCards.associate {
                    it.cardNumber to ("${it.custName} ${it.custSurname} ${it.phoneNumber}")
                }
                loginRepository.getCurrentEmployee().collectLatest { currentEmployee ->
                    if (currentEmployee == null) return@collectLatest
                    storeProductRepository.getAllStoreProductsWithNames().collectLatest { storeProductsWithNames ->
                        val productMap: Map<String, GetAllStoreProductsWithNames> = storeProductsWithNames.associate {
                            it.upc to (it)
                        }
                        employeeRepository.getAllSellers().collectLatest { employees ->
                            repository.getChequesData().collectLatest { chequesData ->

                                val chequesWithSalesMap: Map<String, List<GetChequesData>> = chequesData.groupBy { it.chequeNumber }


                                setViewState(
                                    OrderState.OrderList(
                                        customerCardsData = customerCardMap,
                                        currentEmployee = currentEmployee,
                                        productMap = productMap,
                                        employees = employees,
                                        chequesWithSalesMap = chequesWithSalesMap
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: OrderEvent) {
        when (viewEvent) {
            is OrderEvent.CreateNewOrder -> processNewOrder()
            is OrderEvent.SaveOrder -> processSaveOrder(viewEvent.cheque, viewEvent.saleList)
            else -> {}
        }
    }

    private fun processNewOrder() {
        withViewModelScope {
            val currentState = viewStates().value
            if (currentState is OrderState.OrderList) {
                setViewAction(
                    OrderAction.OpenNewOrderDialog(
                        customerCardsData = currentState.customerCardsData,
                        currentEmployee = currentState.currentEmployee,
                        productMap = currentState.productMap,
                        chequesWithSalesMap = currentState.chequesWithSalesMap
                    )
                )
            }
        }
    }

    private fun processSaveOrder(cheque: Cheque, saleList: List<Sale>) {
        withViewModelScope {
            for (sale in saleList) {
                var storeProduct = storeProductRepository.getStoreProductByUPC(sale.upc)
                if (storeProduct != null)
                    storeProductRepository.insertStoreProduct(
                        StoreProduct(
                            upc = storeProduct.upc,
                            upcProm = "",
                            idProduct = storeProduct.idProduct,
                            sellingPrice = storeProduct.sellingPrice,
                            productsNumber = storeProduct.productsNumber - sale.productNumber,
                            promotionalProduct = storeProduct.promotionalProduct,
                        )
                    )
                saleRepository.insertSale(sale)
            }
            repository.insertCheque(cheque)
        }
    }

}