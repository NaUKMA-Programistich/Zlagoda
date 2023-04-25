package zlagoda.ukma.edu.ua.screens.options.viewmodel

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import zlagoda.ukma.edu.ua.core.file.buildReport
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel
import zlagoda.ukma.edu.ua.data.category.CategoryRepository
import zlagoda.ukma.edu.ua.data.cheque.ChequeRepository
import zlagoda.ukma.edu.ua.data.cheque.ChequeRepositoryImpl
import zlagoda.ukma.edu.ua.data.customer_card.CustomerCardRepository
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepository
import zlagoda.ukma.edu.ua.data.employee.EmployeeRepositoryImpl
import zlagoda.ukma.edu.ua.data.product.ProductRepository
import zlagoda.ukma.edu.ua.data.sale.SaleRepository
import zlagoda.ukma.edu.ua.data.sale.SaleRepositoryImpl
import zlagoda.ukma.edu.ua.data.store_product.StoreProductRepository
import zlagoda.ukma.edu.ua.data.store_product.StoreProductRepositoryImpl
import zlagoda.ukma.edu.ua.db.Category
import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.CustomerCard
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.Product
import zlagoda.ukma.edu.ua.db.Sale
import zlagoda.ukma.edu.ua.db.StoreProduct
import zlagoda.ukma.edu.ua.di.Injection
import zlagoda.ukma.edu.ua.screens.options.utils.buildAll

class OptionsViewModel(
    private val productsRepository: ProductRepository = Injection.productRepository,
    private val categoriesRepository: CategoryRepository = Injection.categoryRepository,
    private val employeeRepository: EmployeeRepository = Injection.employeeRepository,
    private val customerCardRepository: CustomerCardRepository = Injection.customerCardRepository,
    private val chequeCardRepository: ChequeRepository = Injection.chequeCardRepository,
    private val saleRepository: SaleRepository = Injection.saleRepository,
    private val storeProductRepository: StoreProductRepository = Injection.storeProductRepository,
): ViewModel<OptionsState, OptionsAction, OptionsEvent>(
    initialState = OptionsState.EntryDisplay
) {
    override fun obtainEvent(viewEvent: OptionsEvent) {
        when (viewEvent) {
            OptionsEvent.LoadReport -> processLoadReport()
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
                                        collectData(
                                            products = products,
                                            categories = categories,
                                            employees = employees,
                                            customerCards = customerCards,
                                            cheques = cheques,
                                            sales = sales,
                                            storeProducts = storeProducts
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

    private suspend fun collectData(
        products: List<Product>,
        categories: List<Category>,
        employees: List<Employee>,
        customerCards: List<CustomerCard>,
        cheques: List<Cheque>,
        sales: List<Sale>,
        storeProducts: List<StoreProduct>
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

        setViewState(OptionsState.EntryDisplay)
    }
}