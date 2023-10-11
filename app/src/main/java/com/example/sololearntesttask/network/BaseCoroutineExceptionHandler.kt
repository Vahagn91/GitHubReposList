package com.example.sololearntesttask.network

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class BaseCoroutineExceptionHandler(
    override val key: CoroutineContext.Key<*>,
) : CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        try {
            Log.e("Global error", exception.message.toString())
        } catch (ex: Exception) {
            Log.e("Coroutine Exception", ex.message.toString())
        }
    }
}