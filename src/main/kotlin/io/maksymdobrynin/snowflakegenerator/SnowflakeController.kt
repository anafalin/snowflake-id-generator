package io.maksymdobrynin.snowflakegenerator

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/v1")
class SnowflakeController(private val generator: Generator) {

	private val log = LoggerFactory.getLogger(SnowflakeController::class.java)

	@GetMapping("/next-id")
	suspend fun generate(): Long {
		log.info("Generated id...")
		return generator.nextId()
	}
}
