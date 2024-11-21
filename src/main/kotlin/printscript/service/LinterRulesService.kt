package printscript.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import printscript.model.dto.LinterRulesFileDTO
import printscript.model.repository.LinterRulesRepository
import printscript.model.rules.LinterRules
import java.util.UUID

@Service
class LinterRulesService(
    @Autowired private var linterRulesRepository: LinterRulesRepository,
) {
    fun getLinterRulesByUserId(
        userId: String,
        correlationId: UUID,
    ): LinterRules {
        return findOrCreateByUser(userId)
    }

    fun updateLinterRules(
        linterRules: LinterRulesFileDTO,
        userId: String,
    ): LinterRulesFileDTO {
        try {
            var rules = findOrCreateByUser(userId)
            rules.identifierFormat = linterRules.identifier_format
            rules.enableInputOnly = linterRules.enableInputOnly
            rules.enablePrintOnly = linterRules.enablePrintOnly
            rules.enable_print_only = linterRules.enable_print_only
            rules.enable_input_only = linterRules.enable_input_only

            val savedRules = linterRulesRepository.save(rules)

            return LinterRulesFileDTO(
                savedRules.userId ?: "",
                savedRules.identifierFormat ?: "",
                savedRules.enableInputOnly,
                savedRules.enablePrintOnly,
                savedRules.enable_print_only,
                savedRules.enable_input_only,
            )
        } catch (e: Exception) {
            return LinterRulesFileDTO(
                userId,
                "camelcase",
                false,
                false,
                false,
                false,
            )
        }
    }

    private fun findOrCreateByUser(userId: String): LinterRules {
        val rules = linterRulesRepository.findByUserId(userId).orElse(null)
        if (rules == null) {
            println("User not found")
            return createUserById(userId)
        }
        return rules
    }

    private fun createUserById(userId: String): LinterRules {
        val format =
            LinterRules(
                userId = userId,
                identifierFormat = "camelcase",
                enablePrintOnly = false,
                enableInputOnly = false,
                enable_print_only = false,
                enable_input_only = false,
            )
        return linterRulesRepository.save(format)
    }
}
