package printscript.redis.dto

import java.util.UUID

data class ExecutionDataDTO(val correlationId: UUID, val snippetId: String, val language: String, val version: String, val input: String)
