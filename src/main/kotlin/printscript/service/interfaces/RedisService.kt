package printscript.service.interfaces

import org.springframework.stereotype.Service
import printscript.redis.dto.Snippet

@Service
interface RedisService {
    fun formatSnippet(snippet: Snippet): Snippet

    fun lintSnippet(snippet: Snippet): Snippet
}
