package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.DeliveryAddress

class DeliveryAddressBlockingService {
    fun findDeliveryAddress(ids: List<Long>): List<DeliveryAddress> {
        return ids.mapIndexed { index, id ->
            DeliveryAddress(
                id = id,
                roadNameAddress = "도로명 주소 $id",
                detailAddress = "상세 주소 $id"
            )
        }
    }
}