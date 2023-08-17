package com.sim.coroutinemongochat.config

import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing

@Configuration
@EnableReactiveMongoAuditing
class MongoConfig