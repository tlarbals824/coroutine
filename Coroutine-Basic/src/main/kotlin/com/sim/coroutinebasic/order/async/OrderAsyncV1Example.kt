package com.sim.coroutinebasic.order.async

import com.sim.coroutinebasic.kLogger
import com.sim.coroutinebasic.order.async.FirstFinder
import com.sim.coroutinebasic.order.blocking.*

private val log = kLogger()
class OrderAsyncV1Example(
    private val customerService: CustomerFutureService,
    private val productService: ProductRxjava3Service,
    private val storeService: StoreMutinyService,
    private val deliveryAddressService: DeliveryAddressPublisherService,
    private val orderService: OrderReactorService,
) {
    fun execute(userId: Long, productIds: List<Long>) {
        log.info("start")
        customerService.findCustomerFuture(userId).thenAccept { customer ->
            productService.findAllProductsFlowable(productIds)
                .toList()
                .subscribe { products ->
                    val storeIds = products.map { it.storeId }
                    storeService.findStoresMulti(storeIds)
                        .collect().asList()
                        .subscribe()
                        .with { stores ->
                            val daIds = customer.deliveryAddressIds
                            deliveryAddressService.findDeliveryAddressPublisher(daIds)
                                .subscribe(FirstFinder { deliveryAddress ->
                                    orderService.createOrderMono(customer, products, deliveryAddress, stores)
                                        .subscribe { order ->
                                            log.info("order: {}", order)
                                        }
                                })
                        }
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

    val example = OrderAsyncV1Example(
        customerService,
        productService,
        storeService,
        deliveryAddressService,
        orderService,
    )

    example.execute(1, listOf(1, 2, 3))
    Thread.sleep(5000)
}