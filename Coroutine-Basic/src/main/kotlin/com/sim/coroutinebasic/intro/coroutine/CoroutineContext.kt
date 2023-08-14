package com.sim.coroutinebasic.intro.coroutine

import com.sim.coroutinebasic.kLogger
import kotlinx.coroutines.*


private val log = kLogger()
fun main(){
    val threadLocal = ThreadLocal<String>()
    threadLocal.set("hello")
    log.info("tread: {}",Thread.currentThread().name)
    log.info("threadLocal: {}", threadLocal.get())

    runBlocking {
        val context = CoroutineName("custom name")+ Dispatchers.IO+threadLocal.asContextElement()

        launch(context){
            log.info("thread: {}",Thread.currentThread().name)
            log.info("threadLocal: {}", threadLocal.get())
            log.info("coroutine name: {}",coroutineContext[CoroutineName])
        }
    }
}
/**
 * 14:15:13.711 [main] -- tread: main
 * 14:15:13.713 [main] -- threadLocal: hello
 * 14:15:13.734 [DefaultDispatcher-worker-1] -- thread: DefaultDispatcher-worker-1
 * 14:15:13.734 [DefaultDispatcher-worker-1] -- threadLocal: hello
 * 14:15:13.734 [DefaultDispatcher-worker-1] -- coroutine name: CoroutineName(custom name)
 */
