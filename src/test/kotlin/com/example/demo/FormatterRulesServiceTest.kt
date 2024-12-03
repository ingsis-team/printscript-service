package com.example.demo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import printscript.model.dto.FormatterRulesFileDTO
import printscript.model.repository.FormatterRulesRepository
import printscript.model.rules.FormatterRules
import printscript.service.FormatterRulesService
import java.util.UUID

class FormatterRulesServiceTest {
    private lateinit var formatterRulesRepository: FormatterRulesRepository
    private lateinit var formatterRulesService: FormatterRulesService

    @Test
    fun `getFormatterRulesByUserId should return existing rules`() {
        val userId = "user123"
        val correlationId = UUID.randomUUID()
        val existingRules =
            FormatterRules(
                userId = userId,
                spaceBeforeColon = true,
                spaceAfterColon = true,
                spaceAroundEquals = true,
                lineBreakPrintln = 2,
                conditionalIndentation = 4,
            )
        formatterRulesRepository.save(existingRules)

        val result = formatterRulesService.getFormatterRulesByUserId(userId, correlationId)

        assertEquals(existingRules, result)
    }

    @Test
    fun `getFormatterRulesByUserId should create new rules if not found`() {
        val userId = "newUser"
        val correlationId = UUID.randomUUID()

        val result = formatterRulesService.getFormatterRulesByUserId(userId, correlationId)

        assertEquals(userId, result.userId)
        assertEquals(false, result.spaceBeforeColon)
        assertEquals(false, result.spaceAfterColon)
        assertEquals(false, result.spaceAroundEquals)
        assertEquals(0, result.lineBreakPrintln)
        assertEquals(0, result.conditionalIndentation)
    }

    @Test
    fun `updateFormatterRules should update and return updated rules`() {
        val userId = "userToUpdate"
        val existingRules =
            FormatterRules(
                userId = userId,
                spaceBeforeColon = false,
                spaceAfterColon = false,
                spaceAroundEquals = false,
                lineBreakPrintln = 0,
                conditionalIndentation = 0,
            )
        formatterRulesRepository.save(existingRules)

        val updatedDto =
            FormatterRulesFileDTO(
                userId = userId,
                spaceBeforeColon = true,
                spaceAfterColon = true,
                spaceAroundEquals = true,
                lineBreakPrintln = 2,
                conditionalIndentation = 4,
            )

        val result = formatterRulesService.updateFormatterRules(updatedDto, userId)

        assertEquals(updatedDto, result)
        val updatedRules = formatterRulesRepository.findByUserId(userId).orElseThrow()
        assertEquals(true, updatedRules.spaceBeforeColon)
        assertEquals(true, updatedRules.spaceAfterColon)
        assertEquals(true, updatedRules.spaceAroundEquals)
        assertEquals(2, updatedRules.lineBreakPrintln)
        assertEquals(4, updatedRules.conditionalIndentation)
    }
}
