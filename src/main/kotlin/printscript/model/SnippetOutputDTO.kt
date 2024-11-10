package printscript.model

import java.util.UUID

data class SnippetOutputDTO(val snippet: String, val correlationId: UUID, val snippetId: String)
