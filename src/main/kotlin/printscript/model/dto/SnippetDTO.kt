package printscript.model.dto
import java.util.UUID

data class SnippetDTO(
    val correlationId: UUID,
    val snippetId: String,
    val language: String,
    val version: String,
    val input: String,
    val userId: String,
)
