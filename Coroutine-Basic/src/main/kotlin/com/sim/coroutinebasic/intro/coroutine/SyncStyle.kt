package com.sim.coroutinebasic.intro.coroutine

import com.sim.coroutinebasic.intro.entity.ArticleRepository
import com.sim.coroutinebasic.intro.entity.PersonRepository
import com.sim.coroutinebasic.kLogger
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import reactor.core.publisher.Mono

private val log = kLogger()
fun mainBefore(){
    val personRepository = PersonRepository
    val articleRepository = ArticleRepository

    personRepository.findPersonByName("sim")
        .flatMap {person ->
            val future = articleRepository.findArticleById(person.id)

            Mono.fromFuture(future)
                .map { article -> person to article }
        }.subscribe{ (person, article) ->
            log.info("person: {}, article: {}", person, article)
        }
}

fun main() = runBlocking{
    val personRepository = PersonRepository
    val articleRepository = ArticleRepository

    val person = personRepository.findPersonByName("sim").awaitSingle()

    val article = articleRepository.findArticleById(person.id).await()

    log.info("person: {}, article: {}", person, article)
}