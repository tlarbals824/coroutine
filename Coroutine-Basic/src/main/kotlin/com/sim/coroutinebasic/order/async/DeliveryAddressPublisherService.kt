package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.DeliveryAddress
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

class DeliveryAddressPublisherService {
    fun findDeliveryAddressPublisher(ids: List<Long>): Publisher<DeliveryAddress> {
        return Flux.create{ sink ->
            ids.map { id ->
                DeliveryAddress(
                    id = id,
                    roadNameAddress = "도로명 주소 $id",
                    detailAddress = "상세 주소 $id"
                )
            }.forEach {
                Thread.sleep(100)
                sink.next(it)
            }
            sink.complete()
        }
    }
}