package com.sim.coroutinebasic.order.common

data class Customer(
    val id: Long,
    val name: String,
    val deliveryAddressIds: List<Long>,
) {
}