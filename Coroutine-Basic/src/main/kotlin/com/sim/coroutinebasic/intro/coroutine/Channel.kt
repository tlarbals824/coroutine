package com.sim.coroutinebasic.intro.coroutine

import com.sim.coroutinebasic.kLogger
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


private val log = kLogger()
fun main() = runBlocking{
    log.info("Start runBlocking")
    val channel = Channel<Int>()
    launch {
        delay(100)

        for(i in 0 until 5){
            channel.send(i)
        }
        channel.close()
    }

    delay(500)
    for(i in channel){
        log.info("item: {}",i)
    }
    log.info("Finish runBlocking")
}
/**
 * 14:38:38:509 [main] - Start runBlocking
 * 14:38:39:015 [main] - item: 0
 * 14:38:39:017 [main] - item: 1
 * 14:38:39:017 [main] - item: 2
 * 14:38:39:017 [main] - item: 3
 * 14:38:39:017 [main] - item: 4
 * 14:38:39:017 [main] - Finish runBlocking
 */