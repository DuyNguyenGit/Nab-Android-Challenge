package com.p109.nab_android_challenge.util

import com.p109.nab_android_challenge.common.ContextProviders
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestContextProvider : ContextProviders() {
    override val Main: CoroutineContext
        get() = Dispatchers.Unconfined
    override val IO: CoroutineContext
        get() = Dispatchers.Unconfined
}