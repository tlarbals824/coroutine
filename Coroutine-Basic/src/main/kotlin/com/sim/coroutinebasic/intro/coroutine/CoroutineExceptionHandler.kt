package com.sim.coroutinebasic.intro.coroutine

import com.sim.coroutinebasic.kLogger
import kotlinx.coroutines.*
import java.lang.IllegalStateException

private val log = kLogger()
fun main(){
    runBlocking {
        val context = CoroutineName("custom name") +
                CoroutineExceptionHandler{ _,e ->
                    log.error("custom exception handler")
                }

        CoroutineScope(Dispatchers.IO).launch(context) {
            throw IllegalStateException()
        }

        delay(100)
    }
}
/**
 * 14:19:38:267 [DefaultDispatcher-worker-1] - custom exception handler
 */