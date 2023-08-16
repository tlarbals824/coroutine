package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.Customer
import java.util.concurrent.CompletableFuture

class CustomerFutureService {
    fun findCustomerFuture(id: Long) : CompletableFuture<Customer>{
        return CompletableFuture.supplyAsync {
            Thread.sleep(1000)
            Customer(id, "sim", listOf(1, 2, 3))
        }
    }
}