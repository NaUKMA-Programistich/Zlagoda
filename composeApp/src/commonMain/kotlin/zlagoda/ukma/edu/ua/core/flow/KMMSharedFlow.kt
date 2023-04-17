package zlagoda.ukma.edu.ua.core.flow

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.job

class KMMSharedFlow<T>(
    private val origin: SharedFlow<T>,
    private val onComplete: () -> Unit
) : KMMFlow<T>(), SharedFlow<T> by origin {
    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        currentCoroutineContext().job.invokeOnCompletion { onComplete() }
        origin.collect(collector)
    }
}
