package io.maksymdobrynin.snowflakegenerator

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
open class StartupApp(
	private val createAppLog: CreateAppLog,
	private val generatorSettings: GeneratorSettings,
	@Value("\${kubernetes-settings.pod-name}") private var podName: String,
) : CommandLineRunner {

	override fun run(args: Array<String>) {
		createAppLog.create(
			guid = podName,
			datacenterId = generatorSettings.datacenterId,
			workedId = generatorSettings.workedId,
		)
		println(
			"""
			Generated on startup: guid=${podName},
			datacenterId=${generatorSettings.datacenterId},
			workedId=${generatorSettings.workedId}
			""".trimIndent()
		)
	}
}
