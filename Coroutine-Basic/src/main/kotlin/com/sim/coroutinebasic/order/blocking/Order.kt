package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.Customer
import com.sim.coroutinebasic.order.common.DeliveryAddress
import com.sim.coroutinebasic.order.common.Product
import com.sim.coroutinebasic.order.common.Store

data class Order(
    val stores: List<Store>,
    val products: List<Product>,
    val customer: Customer,
    val deliveryAddress: DeliveryAddress,
) {
}