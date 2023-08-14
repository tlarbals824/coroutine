package com.sim.coroutinebasic.basic

import com.sim.coroutinebasic.kLogger
import java.lang.IllegalStateException
import kotlin.coroutines.*

private val log = kLogger()
fun main(){
    var visited = false
    val continuation = object: Continuation<Int>{
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            if(visited){
                log.info("Result:{}", result)
            }else{
                log.info("Visit now")
                visited=true
            }
        }
    }

    continuation.resume(10)
    continuation.resume(10)
    continuation.resumeWithException(
        IllegalStateException()
    )
}
/**
 * 15:12:52:574 [main] - Visit now
 * 15:12:52:575 [main] - Result:Success(10)
 * 15:12:52:576 [main] - Result:Failure(java.lang.IllegalStateException)
 */