package com.vilas.seeder.service

import com.vilas.seeder.firestore.document.SeederDocument
import com.vilas.seeder.repository.SeederRepository
import com.vilas.seeder.web.models.SeederRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*

@Service
class SeederService(
    private val seederRepository: SeederRepository
) {
    fun seedUrls(seederRequest: SeederRequest): Flux<SeederDocument> {
        val seederDocuments = toSeederDocuments(seederRequest)
        return seederRepository.saveAll(seederDocuments).doOnSubscribe {
            logger.info("Seeded ${seederRequest.urls.size} urls successfully with job Id ${seederDocuments[0]?.jobId}.")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SeederService::class.java)

        private fun toSeederDocuments(seederRequest: SeederRequest): List<SeederDocument> {
            val uuid = UUID.randomUUID().toString()
            return seederRequest.urls.map {
                SeederDocument(jobId = uuid, url = it)
            }
        }
    }
}