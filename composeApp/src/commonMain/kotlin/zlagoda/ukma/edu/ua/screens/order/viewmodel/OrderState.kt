package zlagoda.ukma.edu.ua.screens.order.viewmodel

import zlagoda.ukma.edu.ua.db.*


sealed class OrderState {

    data class OrderList(
        val customerCardsData: Map<String, String>,
        val currentEmployee: Employee,
        val productMap: Map<String, GetAllStoreProductsWithNames>,
        val employees: List<Employee>,
        val chequesWithSalesMap: Map<String, List<GetChequesData>>
    ) : OrderState()


    object Loading : OrderState()

}