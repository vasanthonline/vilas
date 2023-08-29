package com.vilas.seeder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SeederApplication

fun main(args: Array<String>) {
	runApplication<SeederApplication>(*args)
}
