package printscript.interfaces

import printscript.redis.dto.Snippet

interface IRedisService {
    fun formatSnippet(snippet: Snippet): Snippet

    fun lintSnippet(snippet: Snippet): Snippet
}
