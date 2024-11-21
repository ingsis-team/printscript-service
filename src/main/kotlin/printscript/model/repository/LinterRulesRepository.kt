package printscript.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import printscript.model.rules.LinterRules
import java.util.Optional
import java.util.UUID

interface LinterRulesRepository : JpaRepository<LinterRules, UUID> {
    fun findByUserId(userId: String): Optional<LinterRules>
}
