package zlagoda.ukma.edu.ua.core.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class KMMFlow<T> : Flow<T> {
    fun observe(block: (T) -> Unit, onCompletion: (Throwable?) -> Unit): Cancelable {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        scope.launch {
            try {
                collect { block(it) }
                onCompletion(null)
            } catch (e: Throwable) { onCompletion(e) }
        }

        return object : Cancelable {
            override fun cancel() {
                scope.cancel()
            }
        }
    }
}

