package com.sim.coroutinebasic.basic

import com.sim.coroutinebasic.kLogger
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

private val log  = kLogger()
object FSMCalculatorWithCPSV1 {
    data class Shared(
        var result: Int = 0,
        var label: Int = 0,
    )

    fun calculate(initialValue: Int, shared: Shared? = null){
        val current = shared ?: Shared()

        val cont = object : Continuation<Int>{
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                current.result = result.getOrThrow()
                this@FSMCalculatorWithCPSV1.calculate(initialValue, current)
            }
        }

        when(current.label){
            0 -> {
                current.label = 1
                initialize(initialValue, cont)
            }
            1 -> {
                val initialized = current.result
                current.label = 2
                addOne(initialized, cont)
            }
            2 -> {
                val added = current.result
                current.label = 3
                multiplyTwo(added, cont)
            }
            3 -> {
                val multiplied = current.result
                log.info("FSM Calculator with CPS Result: {}", multiplied)
                return
            }
        }
    }

    private fun initialize(value: Int, cont: Continuation<Int>){
        log.info("Initial")
        cont.resume(value)
    }

    private fun addOne(value: Int, cont: Continuation<Int>){
        log.info("Add one")
        cont.resume(value+1)
    }

    private fun multiplyTwo(value: Int, cont: Continuation<Int>){
        log.info("Multiply two")
        cont.resume(value*2)
    }
}
fun main(){
    FSMCalculatorWithCPSV1.calculate(5)
}
/**
 * 15:20:52:062 [main] - Initial
 * 15:20:52:063 [main] - Add one
 * 15:20:52:064 [main] - Multiply two
 * 15:20:52:064 [main] - FSM Calculator with CPS Result: 12
 */