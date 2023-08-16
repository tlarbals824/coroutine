package com.sim.coroutinebasic.order.async

import com.sim.coroutinebasic.kLogger
import com.sim.coroutinebasic.order.blocking.*
import com.sim.coroutinebasic.order.common.Customer
import com.sim.coroutinebasic.order.common.DeliveryAddress
import com.sim.coroutinebasic.order.common.Product
import com.sim.coroutinebasic.order.common.Store

private val log = kLogger()

class OrderAsyncV2Example(
    private val customerService: CustomerFutureService,
    private val productService: ProductRxjava3Service,
    private val storeService: StoreMutinyService,
    private val deliveryAddressService: DeliveryAddressPublisherService,
    private val orderService: OrderReactorService,
) {

    class Shared {
        var result: Any? = null
        var label = 0

        lateinit var customer: Customer
        lateinit var products: List<Product>
        lateinit var stores: List<Store>
        lateinit var deliveryAddress: DeliveryAddress
    }

    fun execute(userId: Long, productIds: List<Long>, shared: Shared? = null) {
        val cont = shared ?: Shared()

        when (cont.label) {
            0 -> {
                cont.label = 1
                customerService.findCustomerFuture(userId)
                    .thenAccept { customer ->
                        cont.result = customer
                        execute(userId, productIds, cont)
                    }
            }

            1 -> {
                cont.customer = cont.result as Customer
                cont.label = 2
                productService.findAllProductsFlowable(productIds)
                    .toList()
                    .subscribe { products ->
                        cont.result = products
                        execute(userId, productIds, cont)
                    }
            }

            2 -> {
                cont.products = cont.result as List<Product>
                cont.label = 3
                val storeIds = cont.products.map { it.storeId }
                storeService.findStoresMulti(storeIds)
                    .collect().asList()
                    .subscribe()
                    .with { stores ->
                        cont.result = stores
                        execute(userId, productIds, cont)
                    }
            }

            3 -> {
                cont.stores = cont.result as List<Store>
                cont.label = 4
                val daIds = cont.customer.deliveryAddressIds
                deliveryAddressService.findDeliveryAddressPublisher(daIds)
                    .subscribe(FirstFinder { deliverAddress ->
                        cont.result = deliverAddress
                        execute(userId, productIds, cont)
                    })
            }

            4 -> {
                cont.deliveryAddress = cont.result as DeliveryAddress
                cont.label = 5
                orderService.createOrderMono(
                    cont.customer,
                    cont.products,
                    cont.deliveryAddress,
                    cont.stores,
                ).subscribe { order ->
                    cont.result = order
                    execute(userId, productIds, cont)
                }
            }
            5 -> {
                val order = cont.result as Order
                log.info("order: $order")
            }
        }
    }
}

fun main() {
    val customerService = CustomerFutureService()
    val productService = ProductRxjava3Service()
    val storeService = StoreMutinyService()
    val deliveryAddressService = DeliveryAddressPublisherService()
    val orderService = OrderReactorService()

    val example = OrderAsyncV2Example(
        customerService,
        productService,
        storeService,
        deliveryAddressService,
        orderService
    )

    example.execute(1, listOf(1, 2, 3))
    Thread.sleep(5000)
}