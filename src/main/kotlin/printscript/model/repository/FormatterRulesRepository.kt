package printscript.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import printscript.model.rules.FormatterRules
import java.util.Optional
import java.util.UUID

interface FormatterRulesRepository : JpaRepository<FormatterRules, UUID> {
    fun findByUserId(userId: String): Optional<FormatterRules>
}
