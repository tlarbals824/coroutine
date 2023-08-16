package com.sim.coroutinebasic.order.async

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.atomic.AtomicBoolean


class FirstFinder <T>(
    private val handleComplete: (T) -> Unit
) : Subscriber<T> {
    private val isSent = AtomicBoolean(false)

    override fun onSubscribe(s: Subscription?) {
        s?.request(Long.MAX_VALUE)
    }

    override fun onNext(t: T) {
        if(!isSent.getAndSet(true)){
            handleComplete(t)
        }
    }

    override fun onError(t: Throwable?) {
    }

    override fun onComplete() {
    }
}