package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.Product

class ProductBlockingService {
    fun findAllProductsByIds(ids: List<Long>): List<Product>{
        return ids.map { Product(it, "상품 $it", 1000L + it)}
    }
}