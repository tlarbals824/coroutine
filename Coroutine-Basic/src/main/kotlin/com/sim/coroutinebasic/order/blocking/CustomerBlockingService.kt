package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.Customer

class CustomerBlockingService {
    fun findCustomerById(id: Long) : Customer{
        return Customer(id, "sim", listOf(1,2,3))
    }
}