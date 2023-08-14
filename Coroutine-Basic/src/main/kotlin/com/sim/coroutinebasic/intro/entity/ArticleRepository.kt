package com.sim.coroutinebasic.intro.entity

import java.util.concurrent.CompletableFuture

object ArticleRepository {
    fun findArticleById(id: Long): CompletableFuture<Article> {
        return CompletableFuture.completedFuture(Article(id, "Article $id"))
    }
}