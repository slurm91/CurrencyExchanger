@file:OptIn(ExperimentalCoroutinesApi::class)

package by.vzhilko.currencyexchanger.core.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

inline fun <T, R> StateFlow<T>.mapState(
    scope: CoroutineScope,
    initialValue: R,
    crossinline block: suspend (data: T) -> R
): StateFlow<R> {
    return mapLatest {
        block(it)
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = initialValue
    )
}
