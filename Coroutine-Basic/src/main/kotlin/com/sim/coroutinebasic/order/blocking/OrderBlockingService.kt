package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.Customer
import com.sim.coroutinebasic.order.common.DeliveryAddress
import com.sim.coroutinebasic.order.common.Product
import com.sim.coroutinebasic.order.common.Store

class OrderBlockingService {
    fun createOrder(
        customer: Customer,
        products: List<Product>,
        deliveryAddress: DeliveryAddress,
        stores: List<Store>,
    ):Order{
        return Order(
            stores = stores,
            products = products,
            customer = customer,
            deliveryAddress = deliveryAddress
        )
    }
}