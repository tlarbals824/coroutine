package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.Store
import java.awt.Stroke

class StoreBlockingService {
    fun findStoresByIds(storeIds: List<Long>):List<Store>{
        return storeIds.map{Store(it, "매장 $it")}
    }
}