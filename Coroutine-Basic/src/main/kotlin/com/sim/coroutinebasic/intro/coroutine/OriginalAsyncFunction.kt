package com.sim.coroutinebasic.intro.coroutine

import com.sim.coroutinebasic.kLogger
import java.util.concurrent.CompletableFuture

private val log = kLogger()
private fun nonStructured(){
    log.info("step 1")
    CompletableFuture.runAsync{
        Thread.sleep(1000)
        log.info("Finish run1")
    }
    log.info("step 2")
    CompletableFuture.runAsync{
        Thread.sleep(1000)
        log.info("Finish run2")
    }
    log.info("step 3")
}
fun main(){
    log.info("Start main")
    nonStructured()
    log.info("Finish main")
    Thread.sleep(3000)
}
/**
 * 14:23:31:131 [main] - Start main
 * 14:23:31:132 [main] - step 1
 * 14:23:31:134 [main] - step 2
 * 14:23:31:134 [main] - step 3
 * 14:23:31:134 [main] - Finish main
 * 14:23:32:139 [ForkJoinPool.commonPool-worker-2] - Finish run2
 * 14:23:32:139 [ForkJoinPool.commonPool-worker-1] - Finish run1
 */