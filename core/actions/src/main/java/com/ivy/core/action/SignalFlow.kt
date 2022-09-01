package com.ivy.core.action

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class SignalFlow<T> {
    private val sharedFlow = MutableSharedFlow<T>(replay = 1)
    private var initialSignalSent = false

    abstract fun initialSignal(): T

    suspend fun send(signal: T) {
        sharedFlow.emit(signal)
    }

    suspend fun receive(): Flow<T> {
        if (!initialSignalSent) {
            sharedFlow.emit(initialSignal())
            initialSignalSent = true
        }
        return sharedFlow
    }
}