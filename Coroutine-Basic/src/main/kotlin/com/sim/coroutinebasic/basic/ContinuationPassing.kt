package com.sim.coroutinebasic.basic

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value
import com.sim.coroutinebasic.kLogger
import java.lang.Exception
import kotlin.coroutines.Continuation

private val log = kLogger()
object NormalSeparatedCalculator {
    fun calculate(initialValue: Int){
        var result = initialize(initialValue)
        result = addOne(result)
        result = multiplyTwo(result)
        log.info("Normal Separated Calculator Result: {}",result)
    }

    private fun initialize(value: Int) : Int{
        return value
    }

    private fun addOne(value: Int) : Int{
        return value + 1
    }

    private fun multiplyTwo(value: Int) : Int{
        return value*2
    }
}

object ContinuationPassingCalculator{
    fun calculate(initialValue: Int, continuation: (Int) -> Unit){
        initialize(initialValue){initial ->
            addOne(initial){added ->
                multiplyTwo(added){multiplied ->
                    continuation(multiplied)
                }
            }
        }
    }

    private fun initialize(value: Int, continuation: (Int) -> Unit) {
        log.info("Initial")
        continuation(value)
    }

    private fun addOne(value: Int, continuation: (Int) -> Unit) {
        log.info("Add one")
        continuation(value+1)
    }

    private fun multiplyTwo(value: Int, continuation: (Int) -> Unit){
        log.info("Multiply two")
        continuation(value*2)
    }
}

fun main(){
    NormalSeparatedCalculator.calculate(5)

    ContinuationPassingCalculator.calculate(5){result ->
        log.info("Continuation Passing Calculator Result: {}",result)
    }
}
/**
 * 15:09:46:432 [main] - Normal Separated Calculator Result: 12
 * 15:09:46:440 [main] - Initial
 * 15:09:46:440 [main] - Add one
 * 15:09:46:440 [main] - Multiply two
 * 15:09:46:441 [main] - Continuation Passing Calculator Result: 12
 */