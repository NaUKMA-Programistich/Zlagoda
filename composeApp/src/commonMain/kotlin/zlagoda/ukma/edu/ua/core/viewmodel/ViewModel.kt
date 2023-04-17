package zlagoda.ukma.edu.ua.core.viewmodel

import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zlagoda.ukma.edu.ua.core.flow.KMMStateFlow
import zlagoda.ukma.edu.ua.core.flow.KMMSharedFlow

abstract class ViewModel<State, Action, Event>(initialState: State) : KViewModel() {
    private val _viewStates = MutableStateFlow(initialState)
    fun viewStates() = KMMStateFlow(_viewStates.asStateFlow())
    suspend fun setViewState(state: State) {
        _viewStates.emit(state)
    }

    private val _viewActions = MutableSharedFlow<Action?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    fun viewActions() = KMMSharedFlow(_viewActions.asSharedFlow()) {
        withViewModelScope { _viewActions.emit(null) }
    }
    suspend fun setViewAction(action: Action) {
        _viewActions.emit(action)
    }

    abstract fun obtainEvent(viewEvent: Event)

    fun withViewModelScope(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(block = block)
    }
}
