package zlagoda.ukma.edu.ua.screens.options.viewmodel

import kotlinx.coroutines.flow.collectLatest
import zlagoda.ukma.edu.ua.core.file.buildReport
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.data.cheque.ChequeRepository
import zlagoda.ukma.edu.ua.data.customer_card.CustomerCardRepository
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepository
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.data.sale.SaleRepository
import zlagoda.ukma.edu.ua.data.store_product.StoreProductRepository
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.db.Sale
import zlagoda.ukma.edu.ua.db.StoreProduct
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.core.ktx.buildAll
import zlagoda.ukma.edu.ua.data.login.LoginRepository

class OptionsViewModel(
    private val productsRepository: ProductRepository = Injection.productRepository,
    private val categoriesRepository: CategoryRepository = Injection.categoryRepository,
    private val employeeRepository: EmployeeRepository = Injection.employeeRepository,
    private val customerCardRepository: CustomerCardRepository = Injection.customerCardRepository,
    private val chequeCardRepository: ChequeRepository = Injection.chequeCardRepository,
    private val saleRepository: SaleRepository = Injection.saleRepository,
    private val storeProductRepository: StoreProductRepository = Injection.storeProductRepository,
    private val loginRepository: LoginRepository = Injection.loginRepository
): ViewModel<OptionsState, OptionsAction, OptionsEvent>(
    initialState = OptionsState.Loading
) {
    init {
        withViewModelScope {
            loginRepository.getCurrentEmployee().collectLatest {
                if (it == null) return@collectLatest
                setViewState(OptionsState.EntryDisplay(it))
            }
        }
    }


    override fun obtainEvent(viewEvent: OptionsEvent) {
        when (viewEvent) {
            OptionsEvent.LoadReport -> processLoadReport()
            OptionsEvent.Exit -> processExit()
        }
    }

    private fun processExit() {
        withViewModelScope {
            loginRepository.logout()
            setViewAction(OptionsAction.GoToLogin)
        }
    }

    private fun processLoadReport() {
        withViewModelScope {
            setViewState(OptionsState.Loading)

            productsRepository.getAllProducts().collectLatest { products ->
                categoriesRepository.getAllCategories().collectLatest { categories ->
                    employeeRepository.getAllEmployees().collectLatest { employees ->
                        customerCardRepository.getAllCustomerCards().collectLatest { customerCards ->
                            chequeCardRepository.getAllCheques().collectLatest { cheques ->
                                saleRepository.getAllSales().collectLatest { sales ->
                                    storeProductRepository.getAllStoreProducts().collectLatest { storeProducts ->
                                        loginRepository.getCurrentEmployee().collectLatest { employee ->
                                            if (employee == null) return@collectLatest
                                            collectData(
                                                products = products,
                                                categories = categories,
                                                employees = employees,
                                                customerCards = customerCards,
                                                cheques = cheques,
                                                sales = sales,
                                                storeProducts = storeProducts,
                                                employee = employee
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun collectData(
        products: List<Product>,
        categories: List<Category>,
        employees: List<Employee>,
        customerCards: List<CustomerCard>,
        cheques: List<Cheque>,
        sales: List<Sale>,
        storeProducts: List<StoreProduct>,
        employee: Employee
    ) {
        val csv = buildAll(
            products = products,
            categories = categories,
            employees = employees,
            customerCards = customerCards,
            cheques = cheques,
            sales = sales,
            storeProducts = storeProducts
        )
        buildReport(content = csv)

        setViewState(OptionsState.EntryDisplay(employee))
    }
}