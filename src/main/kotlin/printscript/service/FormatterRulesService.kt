package printscript.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import printscript.model.dto.FormatterRulesFileDTO
import printscript.model.repository.FormatterRulesRepository
import printscript.model.rules.FormatterRules
import java.util.UUID

@Service
class FormatterRulesService(
    @Autowired private var formatterRulesRepository: FormatterRulesRepository,
) {
    fun getFormatterRulesByUserId(
        userId: String,
        correlationId: UUID,
    ): FormatterRules {
        println("userId: $userId")
        return findOrCreateByUser(userId)
    }

    fun updateFormatterRules(
        formatterRules: FormatterRulesFileDTO,
        userId: String,
    ): FormatterRulesFileDTO {
        try {
            println("userId: $userId")
            val rules = findOrCreateByUser(userId)
            print("old rules: $rules")
            rules.spaceBeforeColon = formatterRules.spaceBeforeColon
            rules.spaceAfterColon = formatterRules.spaceAfterColon
            rules.spaceAroundEquals = formatterRules.spaceAroundEquals
            rules.lineBreakPrintln = formatterRules.lineBreakPrintln
            rules.conditionalIndentation = formatterRules.conditionalIndentation
            formatterRulesRepository.save(rules)
            println(rules)
            return FormatterRulesFileDTO(
                userId,
                rules.spaceBeforeColon,
                rules.spaceAfterColon,
                rules.spaceAroundEquals,
                rules.lineBreakPrintln,
                rules.conditionalIndentation,
            )
        } catch (e: Exception) {
            throw RuntimeException("Could not save rules")
        }
    }

    private fun findOrCreateByUser(userId: String): FormatterRules {
        val rules = formatterRulesRepository.findByUserId(userId).orElse(null)
        println("rules: $rules")
        if (rules == null) {
            println("User not found")
            return createUserById(userId)
        }
        return rules
    }

    private fun createUserById(userId: String): FormatterRules {
        val format =
            FormatterRules(
                userId = userId,
                spaceBeforeColon = false,
                spaceAfterColon = false,
                spaceAroundEquals = false,
                lineBreakPrintln = 0,
                conditionalIndentation = 0,
            )
        return formatterRulesRepository.save(format)
    }
}
