package printscript.redis.consumer

data class SnippetFormatMessage(val userId: String, val snippetId: String, val snippet: String)
