package zlagoda.ukma.edu.ua.screens.order.viewmodel

import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.Employee
import zlagoda.ukma.edu.ua.db.GetAllStoreProductsWithNames


sealed class OrderAction {
    data class OpenEditOrderDialog(val cheque: Cheque) : OrderAction()
    object OpenSearchDialog: OrderAction()

    data class OpenNewOrderDialog(
        val customerCardsData: Map<String, String>,
        val currentEmployee: Employee,
        val productMap: Map<String, GetAllStoreProductsWithNames>
    ): OrderAction()

}