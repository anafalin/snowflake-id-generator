package io.maksymdobrynin.snowflakegenerator

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/v1")
class SnowflakeController(
	@Value("\${kubernetes-settings.podName}") private var podName: String,
	private val generator: Generator
) {
	private val log = LoggerFactory.getLogger(SnowflakeController::class.java)

	@GetMapping("/next-id")
	suspend fun generate(): Long {
		log.info("Generated id in pod=$podName")
		return generator.nextId()
	}
}
