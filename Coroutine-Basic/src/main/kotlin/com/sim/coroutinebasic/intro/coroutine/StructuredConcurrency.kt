package com.sim.coroutinebasic.intro.coroutine

import com.sim.coroutinebasic.kLogger
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = kLogger()
private suspend fun structured() = coroutineScope{
    log.info("step 1")
    launch {
        delay(1000)
        log.info("Finish run1")
    }
    log.info("step 2")
    launch {
        delay(100)
        log.info("Finish run2")
    }
    log.info("step 3")
}

fun main()= runBlocking{
    log.info("Start runBlocking")
    structured()
    log.info("Finish runBlocking")
}
/**
 * 14:26:20:962 [main] - Start runBlocking
 * 14:26:20:965 [main] - step 1
 * 14:26:20:965 [main] - step 2
 * 14:26:20:966 [main] - step 3
 * 14:26:21:972 [main] - Finish run1
 * 14:26:22:981 [main] - Finish run2
 * 14:26:22:981 [main] - Finish runBlocking
 */