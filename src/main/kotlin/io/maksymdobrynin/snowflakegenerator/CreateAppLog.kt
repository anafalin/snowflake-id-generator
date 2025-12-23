package io.maksymdobrynin.snowflakegenerator

interface CreateAppLog {
	fun create(
		guid: String,
		datacenterId: Long,
		workedId: Long,
	)
}
