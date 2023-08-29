package com.vilas.seeder.web.handler

import com.vilas.seeder.facade.SeederFacade
import com.vilas.seeder.web.models.SeederRequest
import com.vilas.seeder.web.models.SeederResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class SeederHandler(
    private val seederFacade: SeederFacade
) {
    fun seedUrls(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(SeederRequest::class.java)
            .doOnSubscribe { logger.info("Received request to seed urls") }
            .flatMap { seederFacade.seedUrls(it) }
            .flatMap {
                ServerResponse.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .bodyValue("Urls successfully seeded.")
            }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SeederHandler::class.java)
    }
}
