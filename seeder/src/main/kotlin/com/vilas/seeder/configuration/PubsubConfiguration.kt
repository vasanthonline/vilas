package com.vilas.seeder.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter
import com.google.cloud.spring.pubsub.support.converter.PubSubMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PubsubConfiguration {
    @Bean
    fun pubSubMessageConverter(objectMapper: ObjectMapper): PubSubMessageConverter =
        JacksonPubSubMessageConverter(objectMapper)
}