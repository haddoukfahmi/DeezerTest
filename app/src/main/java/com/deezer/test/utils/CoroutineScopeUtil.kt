package com.deezer.test.utils

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

object CoroutineScopeUtil {
    private val TAG = "ViewModelError"

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "$exception handled !")
    }
    private val parenJob = Job()
    val coroutineContext: CoroutineContext get() = parenJob + Dispatchers.IO + handler
    val scope = CoroutineScope(coroutineContext)

}