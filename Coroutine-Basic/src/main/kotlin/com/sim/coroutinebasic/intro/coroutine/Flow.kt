package com.sim.coroutinebasic.intro.coroutine

import com.sim.coroutinebasic.kLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

private val log = kLogger()
private fun range(n: Int): Flow<Int> {
    return flow {
        for(i in 0 until n){
            delay(100)
            emit(i)
        }
    }
}

fun main() = runBlocking {
    log.info("Start runBlocking")
    range(5).collect{
        log.info("item: {}",it)
    }
    log.info("Finish runBlocking")
}
/**
 * 14:36:09:459 [main] - Start runBlocking
 * 14:36:09:572 [main] - item: 0
 * 14:36:09:677 [main] - item: 1
 * 14:36:09:779 [main] - item: 2
 * 14:36:09:884 [main] - item: 3
 * 14:36:09:989 [main] - item: 4
 * 14:36:09:990 [main] - Finish runBlocking
 */