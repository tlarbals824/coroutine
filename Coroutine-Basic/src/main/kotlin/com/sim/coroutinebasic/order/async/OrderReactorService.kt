package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.Customer
import com.sim.coroutinebasic.order.common.DeliveryAddress
import com.sim.coroutinebasic.order.common.Product
import com.sim.coroutinebasic.order.common.Store
import reactor.core.publisher.Mono

class OrderReactorService {
    fun createOrderMono(
        customer: Customer,
        products: List<Product>,
        deliveryAddress: DeliveryAddress,
        stores: List<Store>,
    ): Mono<Order> {
        return Mono.create { sink ->
            Thread.sleep(1000)
            sink.success(
                Order(
                    stores = stores,
                    products = products,
                    customer = customer,
                    deliveryAddress = deliveryAddress
                )
            )
        }
    }
}