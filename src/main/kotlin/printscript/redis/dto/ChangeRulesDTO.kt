package printscript.redis.dto

import java.util.UUID

data class ChangeRulesDTO(val userId: String, val rules: List<Rule>, val snippets: List<ExecutionDataDTO>, val correlationId: UUID)
