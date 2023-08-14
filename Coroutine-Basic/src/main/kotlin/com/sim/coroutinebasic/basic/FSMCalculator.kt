package com.sim.coroutinebasic.basic

import com.sim.coroutinebasic.kLogger

private val log = kLogger()

object NormalCalculator{
    fun calculate(initialValue: Int){
        var result = initialValue
        result+=1
        result*=2
        log.info("Normal Calculator Result: {}",result)
    }
}
object FSMCalculator {
    data class Shared(
        var result: Int = 0,
        var label: Int = 0,
    )

    fun calculate(initialValue: Int, shared: Shared? = null){
        val current = shared ?: Shared()

        when(current.label){
            0 -> {
                current.result = initialValue
                current.label = 1
            }
            1 -> {
                current.result +=1
                current.label = 2
            }
            2 -> {
                current.result *=2
                current.label = 3
            }
            3 -> {
                log.info("FSM Calculator Result: {}", current.result)
                return
            }
        }

        this.calculate(initialValue, current)
    }
}

fun main(){
    NormalCalculator.calculate(5)
    FSMCalculator.calculate(5)
}
/**
 * 14:59:11:236 [main] - Normal Calculator Result: 12
 * 14:59:11:238 [main] - FSM Calculator Result: 12
 */