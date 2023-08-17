package com.sim.coroutinemongochat.handler

import com.sim.coroutinemongochat.service.Chat
import com.sim.coroutinemongochat.service.ChatCoroutineService
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ChatCoroutineWebSocketHandler(
    private val chatService: ChatCoroutineService,
) : WebSocketHandler{
    override fun handle(session: WebSocketSession): Mono<Void> {
        return mono{
            val iam = session.attributes["iam"] as String
            val chatFlux: Flux<Chat> = chatService.register(iam)

            chatService.sendChat("System", iam, iam+"님 채팅방에 오신 것을 환영합니다.")

            session.receive().subscribe{webSocketMessage ->
                val payload = webSocketMessage.payloadAsText
                val splits = payload.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val to = splits[0].trim{ it <= ' '}
                val message = splits[1].trim { it <= ' ' }

                launch {
                    chatService.sendChat(iam, to, message)
                }
            }

            session.send(chatFlux.map { chat: Chat ->
                session.textMessage(
                    chat.from + ": " + chat.message
                )
            }).awaitFirstOrNull()
        }

    }
}