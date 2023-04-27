package zlagoda.ukma.edu.ua.screens.order.viewmodel

import zlagoda.ukma.edu.ua.db.Cheque
import zlagoda.ukma.edu.ua.db.Sale


sealed class OrderEvent {

    object CreateNewOrder: OrderEvent()

    data class DeleteOrder(val chequeNumber : String) : OrderEvent()

    data class SaveOrder(val cheque: Cheque, val saleList : List<Sale>) : OrderEvent()

}