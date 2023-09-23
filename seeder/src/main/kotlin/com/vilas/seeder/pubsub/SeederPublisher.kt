package com.vilas.seeder.pubsub

import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.vilas.seeder.model.SeederMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class SeederPublisher(
    private val pubSubTemplate: PubSubTemplate,
    @Value("\${pubsub.seeder.topic-name}")
    private val topic: String
) {
    fun publish(seederMessage: SeederMessage): Mono<String> {
        val future = pubSubTemplate.publish(topic, seederMessage).completable()
        return Mono.fromFuture(future)
            .doOnSuccess {
                logger.info("Successfully published to $topic: $seederMessage.")
            }
            .doOnError {
                logger.error("Failed to publish to $topic: $seederMessage. Error: $it.")
            }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SeederPublisher::class.java)
    }
}