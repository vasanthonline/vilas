package com.vilas.seeder.web.config

import com.vilas.seeder.web.handler.SeederHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig(val seederHandler: SeederHandler) {
    @Bean
    fun timeslotRouter(): RouterFunction<ServerResponse> {
        return router {
            BASE_URI.nest {
                POST("/seed")
                    .invoke { seederHandler.seedUrls(it) }
            }
        }
    }

    companion object {
        const val BASE_URI = "/api"
    }
}
