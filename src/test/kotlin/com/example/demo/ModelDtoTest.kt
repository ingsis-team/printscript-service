package com.example.demo
import org.junit.jupiter.api.Test
import printscript.model.dto.FormatterFileDTO
import printscript.model.dto.FormatterRulesFileDTO
import printscript.model.dto.LinterFileDTO
import printscript.model.dto.LinterRulesFileDTO
import printscript.model.dto.SnippetDTO
import printscript.model.dto.SnippetOutputDTO
import printscript.model.dto.TestDTO
import printscript.model.dto.ValidationResult
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ModelDtoTest {
    @Test
    fun `FormatterFileDTO should be correctly initialized`() {
        val formatterFileDTO =
            FormatterFileDTO(
                spaceBeforeColon = true,
                spaceAfterColon = true,
                spaceAroundEquals = true,
                lineBreakPrintln = 15,
                conditionalIndentation = 4,
            )

        assertEquals(true, formatterFileDTO.spaceBeforeColon)
        assertEquals(true, formatterFileDTO.spaceAfterColon)
        assertEquals(true, formatterFileDTO.spaceAroundEquals)
        assertEquals(15, formatterFileDTO.lineBreakPrintln)
        assertEquals(4, formatterFileDTO.conditionalIndentation)
    }

    @Test
    fun `FormatterRulesFileDTO should be correctly initialized`() {
        val formatterRulesFileDTO =
            FormatterRulesFileDTO(
                userId = "user123",
                spaceBeforeColon = true,
                spaceAfterColon = true,
                spaceAroundEquals = true,
                lineBreakPrintln = 15,
                conditionalIndentation = 4,
            )

        assertEquals("user123", formatterRulesFileDTO.userId)
        assertEquals(true, formatterRulesFileDTO.spaceBeforeColon)
        assertEquals(true, formatterRulesFileDTO.spaceAfterColon)
        assertEquals(true, formatterRulesFileDTO.spaceAroundEquals)
        assertEquals(15, formatterRulesFileDTO.lineBreakPrintln)
        assertEquals(4, formatterRulesFileDTO.conditionalIndentation)
    }

    @Test
    fun `LinterFileDTO should be correctly initialized`() {
        val linterFileDTO =
            LinterFileDTO(
                identifier_format = "snake_case",
                enablePrintOnly = true,
                enableInputOnly = false,
            )

        assertEquals("snake_case", linterFileDTO.identifier_format)
        assertEquals(true, linterFileDTO.enablePrintOnly)
        assertEquals(false, linterFileDTO.enableInputOnly)
    }

    @Test
    fun `LinterRulesFileDTO should be correctly initialized`() {
        val linterRulesFileDTO =
            LinterRulesFileDTO(
                userId = "user456",
                identifier_format = "camelCase",
                enablePrintOnly = true,
                enableInputOnly = false,
            )

        assertEquals("user456", linterRulesFileDTO.userId)
        assertEquals("camelCase", linterRulesFileDTO.identifier_format)
        assertEquals(true, linterRulesFileDTO.enablePrintOnly)
        assertEquals(false, linterRulesFileDTO.enableInputOnly)
    }

    @Test
    fun `SnippetDTO should be correctly initialized`() {
        val correlationId = UUID.randomUUID()
        val snippetDTO =
            SnippetDTO(
                correlationId = correlationId,
                snippetId = "snippet123",
                language = "Kotlin",
                version = "1.0",
                input = "val x = 5",
                userId = "user789",
            )

        assertNotNull(snippetDTO.correlationId)
        assertEquals("snippet123", snippetDTO.snippetId)
        assertEquals("Kotlin", snippetDTO.language)
        assertEquals("1.0", snippetDTO.version)
        assertEquals("val x = 5", snippetDTO.input)
        assertEquals("user789", snippetDTO.userId)
    }

    @Test
    fun `SnippetOutputDTO should be correctly initialized`() {
        val correlationId = UUID.randomUUID()
        val snippetOutputDTO =
            SnippetOutputDTO(
                snippet = "val result = x + 1",
                correlationId = correlationId,
                snippetId = "snippet123",
            )

        assertNotNull(snippetOutputDTO.correlationId)
        assertEquals("snippet123", snippetOutputDTO.snippetId)
        assertEquals("val result = x + 1", snippetOutputDTO.snippet)
    }

    @Test
    fun `TestDTO should be correctly initialized`() {
        val testDTO =
            TestDTO(
                input = "val x = 5",
                snippet = "val x = 5 + 1",
                output = listOf("6"),
                envVars = "PATH=/usr/bin",
            )

        assertEquals("val x = 5", testDTO.input)
        assertEquals("val x = 5 + 1", testDTO.snippet)
        assertEquals(listOf("6"), testDTO.output)
        assertEquals("PATH=/usr/bin", testDTO.envVars)
    }

    @Test
    fun `ValidationResult should be correctly initialized`() {
        val validationResult =
            ValidationResult(
                isValid = true,
                rule = "No extra spaces",
                line = 5,
                column = 10,
            )

        assertEquals(true, validationResult.isValid)
        assertEquals("No extra spaces", validationResult.rule)
        assertEquals(5, validationResult.line)
        assertEquals(10, validationResult.column)
    }
}
