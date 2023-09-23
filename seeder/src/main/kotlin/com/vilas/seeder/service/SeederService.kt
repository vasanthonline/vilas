package com.vilas.seeder.service

import com.vilas.seeder.firestore.document.SeederDocument
import com.vilas.seeder.firestore.repository.SeederRepository
import com.vilas.seeder.model.SeederMessage
import com.vilas.seeder.pubsub.SeederPublisher
import com.vilas.seeder.web.models.SeederRequest
import kotlinx.coroutines.reactive.collect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.util.*

@Service
class SeederService(
    private val seederRepository: SeederRepository,
    private val seederPublisher: SeederPublisher,
) {
    fun seedUrls(seederRequest: SeederRequest): Flux<SeederDocument> {
        val (jobId, seederDocuments) = toSeederDocuments(seederRequest)

        return seederRepository.saveAll(seederDocuments)
            .doOnSubscribe {
                logger.info("Seeded ${seederRequest.urls.size} urls successfully with job Id ${jobId}.")
            }
            .collectMap { seederDocument ->
                val seederMessage = SeederMessage(
                    documentId = seederDocument.id,
                    url = seederDocument.url,
                    jobId = seederDocument.jobId
                )
                logger.info("Publishing seeder url ${seederDocument.url} with message: $seederMessage")
                seederPublisher.publish(seederMessage)
                .doOnSubscribe {
                    logger.info("Published seeder url ${seederDocument.url} with message: $seederMessage")
                }
            }.flatMapIterable {
                it.values
            }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SeederService::class.java)

        private fun toSeederDocuments(seederRequest: SeederRequest): Pair<String, List<SeederDocument>> {
            val jobId = UUID.randomUUID().toString()
            return Pair(jobId, seederRequest.urls.map {
                val documentId = UUID.randomUUID().toString()
                SeederDocument(id = documentId, jobId = jobId, url = it)
            })
        }
    }
}