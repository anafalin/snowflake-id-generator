package io.maksymdobrynin.snowflakegenerator
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime.now

@Component
@Transactional
open class CreateAppLogInDb(
	private val jdbcTemplate: NamedParameterJdbcTemplate,
) : CreateAppLog {

	override fun create(
		guid: String,
		datacenterId: Long,
		workedId: Long
	) {
		val params = mapOf(
			"guid" to guid,
			"datacenterId" to datacenterId,
			"workedId" to workedId,
			"createdAt" to now(),
		)

		jdbcTemplate.update(
			"""
            INSERT INTO app_log (
                guid, datacenter_id, worked_id, created_at
			)
            VALUES (
                :guid, :datacenterId, :workedId, :createdAt
            )
            """.trimIndent(),
			params
		)
	}
}
