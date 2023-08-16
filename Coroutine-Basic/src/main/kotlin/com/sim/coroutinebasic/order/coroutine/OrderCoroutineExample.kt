package com.sim.coroutinebasic.order.coroutine

import com.sim.coroutinebasic.kLogger
import com.sim.coroutinebasic.order.blocking.*
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.rx3.await

private val log = kLogger()
class OrderCoroutineExample(
    private val customerService: CustomerFutureService,
    private val productService: ProductRxjava3Service,
    private val storeService: StoreMutinyService,
    private val deliveryAddressService: DeliveryAddressPublisherService,
    private val orderService: OrderReactorService,
) {
    suspend fun execute(userId: Long, productIds: List<Long>) : Order{
        val customer = customerService.findCustomerFuture(userId).await()

        val products = productService.findAllProductsFlowable(productIds)
            .toList().await()

        val storeIds = products.map { it.storeId }
        val stores = storeService.findStoresMulti(storeIds)
            .collect().asList().awaitSuspending()

        val daIds = customer.deliveryAddressIds
        val deliveryAddress = deliveryAddressService.findDeliveryAddressPublisher(daIds)
            .awaitFirst()

        val order = orderService.createOrderMono(
            customer, products, deliveryAddress, stores,
        ).awaitSingle()

        return order
    }
}

suspend fun main() {
    val customerService = CustomerFutureService()
    val productService = ProductRxjava3Service()
    val storeService = StoreMutinyService()
    val deliveryAddressService = DeliveryAddressPublisherService()
    val orderService = OrderReactorService()

    val example = OrderCoroutineExample(
        customerService,
        productService,
        storeService,
        deliveryAddressService,
        orderService
    )

    val order = example.execute(1, listOf(1, 2, 3))
    log.info("order: {}", order)
}