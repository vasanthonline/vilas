package com.vilas.seeder.facade

import com.vilas.seeder.service.SeederService
import com.vilas.seeder.web.models.SeederRequest
import com.vilas.seeder.web.models.SeederResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class SeederFacade (
    private val seederService: SeederService
) {
    fun seedUrls(seederRequest: SeederRequest): Mono<SeederResponse> {
        return seederService.seedUrls(seederRequest)
            .doOnSubscribe { logger.info("Seeded urls.") }
            .map {
                SeederResponse(jobId = it.jobId ?: "")
            }.toMono()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SeederFacade::class.java)
    }
}
