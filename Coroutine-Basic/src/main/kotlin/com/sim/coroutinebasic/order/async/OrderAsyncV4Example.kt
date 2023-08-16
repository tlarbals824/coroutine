package com.sim.coroutinebasic.order.async

import com.sim.coroutinebasic.kLogger
import com.sim.coroutinebasic.order.blocking.*
import com.sim.coroutinebasic.order.common.Customer
import com.sim.coroutinebasic.order.common.DeliveryAddress
import com.sim.coroutinebasic.order.common.Product
import com.sim.coroutinebasic.order.common.Store
import io.reactivex.rxjava3.core.Single
import io.smallrye.mutiny.Uni
import kotlinx.coroutines.rx3.await
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.properties.Delegates

private val log = kLogger()

class OrderAsyncV4Example(
    private val customerService: CustomerFutureService,
    private val productService: ProductRxjava3Service,
    private val storeService: StoreMutinyService,
    private val deliveryAddressService: DeliveryAddressPublisherService,
    private val orderService: OrderReactorService,
) {
    private class CustomContinuation(
        private val completion: Continuation<Any>,
    ) : Continuation<Any> {
        var result: Any? = null
        var label = 0

        lateinit var that: OrderAsyncV4Example
        var userId by Delegates.notNull<Long>()
        lateinit var productIds: List<Long>

        lateinit var customer: Customer
        lateinit var products: List<Product>
        lateinit var stores: List<Store>
        lateinit var deliveryAddress: DeliveryAddress

        override val context: CoroutineContext
            get() = completion.context

        override fun resumeWith(result: Result<Any>) {
            this.result = result.getOrThrow()
            that.execute(0, emptyList(), this)
        }

        fun complete(value: Any) {
            completion.resume(value)
        }
    }

    fun execute(userId: Long, productIds: List<Long>, continuation: Continuation<Any>) {
        val cont = if (continuation is CustomContinuation) {
            continuation
        } else {
            CustomContinuation(continuation).apply {
                that = this@OrderAsyncV4Example
                this.userId = userId
                this.productIds = productIds
            }
        }

        when (cont.label) {
            0 -> {
                cont.label = 1
                customerService.findCustomerFuture(userId)
                    .await(cont)
            }

            1 -> {
                cont.customer = cont.result as Customer
                cont.label = 2
                productService.findAllProductsFlowable(cont.productIds)
                    .toList().await(cont)
            }

            2 -> {
                cont.products = cont.result as List<Product>
                cont.label = 3
                val storeIds = cont.products.map { it.storeId }
                storeService.findStoresMulti(storeIds)
                    .collect().asList()
                    .awaitSuspending(cont)

            }

            3 -> {
                cont.stores = cont.result as List<Store>
                cont.label = 4
                val daIds = cont.customer.deliveryAddressIds
                deliveryAddressService.findDeliveryAddressPublisher(daIds)
                    .awaitFirst(cont)
            }

            4 -> {
                cont.deliveryAddress = cont.result as DeliveryAddress
                cont.label = 5
                orderService.createOrderMono(
                    cont.customer,
                    cont.products,
                    cont.deliveryAddress,
                    cont.stores,
                ).awaitSingle(cont)
            }

            5 -> {
                val order = cont.result as Order
                cont.complete(order)
            }
        }
    }

    private fun <T> CompletableFuture<T>.await(
        cont: Continuation<T>
    ) {
        this.thenAccept(cont::resume)
    }

    private fun <T> Single<T>.await(cont: Continuation<T>) where T : Any {
        this.subscribe(cont::resume)
    }

    private fun <T> Uni<T>.awaitSuspending(cont: Continuation<T>) {
        this.subscribe().with(cont::resume)
    }

    private fun <T> Publisher<T>.awaitFirst(cont: Continuation<T>) {
        this.subscribe(FirstFinder(cont::resume))
    }

    private fun <T> Mono<T>.awaitSingle(cont: Continuation<T>) {
        this.subscribe(cont::resume)
    }
}

fun main() {
    val customerService = CustomerFutureService()
    val productService = ProductRxjava3Service()
    val storeService = StoreMutinyService()
    val deliveryAddressService = DeliveryAddressPublisherService()
    val orderService = OrderReactorService()

    val example = OrderAsyncV4Example(
        customerService,
        productService,
        storeService,
        deliveryAddressService,
        orderService
    )

    val cont = Continuation<Any>(EmptyCoroutineContext) {
        log.info("result: $it")
    }

    example.execute(1, listOf(1, 2, 3), cont)
    Thread.sleep(5000)
}