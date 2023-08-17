package com.sim.coroutinemongochat.repository

import com.sim.coroutinemongochat.entity.ChatDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository

interface ChatMongoCoroutineRepository: ReactiveMongoRepository<ChatDocument, ObjectId> {
}