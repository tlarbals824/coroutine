package com.sim.coroutinebasic.basic

import com.sim.coroutinebasic.kLogger
import reactor.kotlin.core.publisher.toMono

private val log  = kLogger()
class FSM{
    fun execute(label: Int = 0){
        var nextLabel: Int? = null

        when(label){
            0 -> {
                log.info("Initial")
                nextLabel=1
            }
            1 -> {
                log.info("State1")
                nextLabel=2
            }
            2->{
                log.info("State2")
                nextLabel=3
            }
            3->{
                log.info("End")
            }
        }

        if(nextLabel != null){
            this.execute(nextLabel)
        }
    }
}

fun main(){
    val fsm = FSM()
    fsm.execute()
}
/**
 * 14:53:21:216 [main] - Initial
 * 14:53:21:217 [main] - State1
 * 14:53:21:217 [main] - State2
 * 14:53:21:217 [main] - End
 */
