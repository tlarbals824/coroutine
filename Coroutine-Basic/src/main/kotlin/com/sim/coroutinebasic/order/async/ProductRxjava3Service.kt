package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.kLogger
import com.sim.coroutinebasic.order.common.Product
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable

private val log = kLogger()
class ProductRxjava3Service {
    fun findAllProductsFlowable(ids: List<Long>): Flowable<Product>{
        return Flowable.create({emitter ->
            ids.forEach{
                Thread.sleep(100)
                val p = Product(it, "상품 $it", 1000L + it)
                emitter.onNext(p)
            }
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }
}