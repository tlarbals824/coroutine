package com.sim.coroutinebasic.order.blocking

import com.sim.coroutinebasic.order.common.Store
import io.smallrye.mutiny.Multi

class StoreMutinyService {
    fun findStoresMulti(storeIds: List<Long>): Multi<Store> {
        return Multi.createFrom().emitter{
            storeIds.map {id ->
                Store(id, "매장 $id")
            }.forEach{store ->
                Thread.sleep(100)
                it.emit(store)
            }
            it.complete()
        }
    }
}