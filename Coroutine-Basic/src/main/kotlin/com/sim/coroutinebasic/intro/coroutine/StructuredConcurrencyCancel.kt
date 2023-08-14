package com.sim.coroutinebasic.intro.coroutine

import com.sim.coroutinebasic.kLogger
import kotlinx.coroutines.*

private val log = kLogger()
private suspend fun structured() = coroutineScope{
    launch {
        try{
            delay(1000)
            log.info("Finish launch1")
        }catch (e : CancellationException){
            log.info("Job1 is cancelled")
        }
    }
    launch {
        try{
            delay(500)
            log.info("Finish launch2")
        }catch (e : CancellationException){
            log.info("Job2 is cancelled")
        }
    }

    delay(200)
    this.cancel()
}

fun main()= runBlocking{
    log.info("Start runBlocking")
    try{
        structured()
    } catch (e: CancellationException){
        log.info("Job is cancelled")
    }
    log.info("Finish runBlocking")
}
/**
 * 14:32:36:261 [main] - Start runBlocking
 * 14:32:36:470 [main] - Job1 is cancelled
 * 14:32:36:471 [main] - Job2 is cancelled
 * 14:32:36:471 [main] - Job is cancelled
 * 14:32:36:471 [main] - Finish runBlocking
 */