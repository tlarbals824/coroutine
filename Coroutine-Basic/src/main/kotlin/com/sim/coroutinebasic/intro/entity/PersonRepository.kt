package com.sim.coroutinebasic.intro.entity

import reactor.core.publisher.Mono

object PersonRepository {
    fun findPersonByName(name: String): Mono<Person> {
        return Mono.just(Person(1, name))
    }
}