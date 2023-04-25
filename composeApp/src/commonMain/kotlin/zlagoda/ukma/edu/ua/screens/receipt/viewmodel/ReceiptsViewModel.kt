package zlagoda.ukma.edu.ua.screens.receipt.viewmodel
import zlagoda.ukma.edu.ua.core.viewmodel.ViewModel


class ReceiptsViewModel(

): ViewModel<ReceiptsState, ReceiptsAction, ReceiptsEvent>(
    initialState = ReceiptsState.Loading,
) {
    init {

    }

    override fun obtainEvent(viewEvent: ReceiptsEvent) {

    }

}