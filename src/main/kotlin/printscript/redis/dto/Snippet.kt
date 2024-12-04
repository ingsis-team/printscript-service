package printscript.redis.dto

import java.util.UUID

data class Snippet(val userId: String, val id: String, val content: String, val correlationID: UUID) {
    override fun toString(): String {
        return "Snippet{ userId:$userId, id:$id, content:$content, correlationId:$correlationID"
    }
}
