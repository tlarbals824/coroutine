package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.kLogger

private val log = kLogger()
class OrderBlockingExample(
    private val customerService: CustomerBlockingService,
    private val productService: ProductBlockingService,
    private val storeService: StoreBlockingService,
    private val deliveryAddressService: DeliveryAddressBlockingService,
    private val orderService: OrderBlockingService,
) {
    fun execute(userId: Long, productIds: List<Long>): Order{
        val customer = customerService.findCustomerById(userId)

        val products = productService.findAllProductsByIds(productIds)

        val storeIds = products.map { it.storeId }
        val stores = storeService.findStoresByIds(storeIds)

        val daIds = customer.deliveryAddressIds
        val deliveryAddress = deliveryAddressService.findDeliveryAddress(daIds).first()

        val order = orderService.createOrder(customer, products, deliveryAddress, stores)

        return order
    }
}

fun main(){
    val customerService = CustomerBlockingService()
    val productService = ProductBlockingService()
    val storeService = StoreBlockingService()
    val deliveryAddressService = DeliveryAddressBlockingService()
    val orderService = OrderBlockingService()

    val example = OrderBlockingExample(
        customerService,
        productService,
        storeService,
        deliveryAddressService,
        orderService
    )

    val order = example.execute(1, listOf(1,2,3))
    log.info("order: {}",order)
}