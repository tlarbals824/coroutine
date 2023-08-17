package com.sim.coroutinemongochat.config

import com.sim.coroutinemongochat.handler.ChatCoroutineWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping

@Configuration
class MappingConfig {
    @Bean
    fun simpleUrlHandlerMapping(
        chatWebSocketHandler: ChatCoroutineWebSocketHandler
    ) : SimpleUrlHandlerMapping{
         val urlMapper = mapOf(pair = Pair("/chat", chatWebSocketHandler))
        return SimpleUrlHandlerMapping().also {
            it.order = 1
            it.urlMap = urlMapper
        }
    }
}