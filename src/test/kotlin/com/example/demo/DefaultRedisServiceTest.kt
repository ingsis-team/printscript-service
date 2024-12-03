package com.example.demo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.util.UUID

class DefaultRedisServiceTest {
    // Servicio simulado
    private val snippetService =
        object : PrintScriptService {
            override fun format(
                id: String,
                inputStream: ByteArrayInputStream,
                version: String,
                userId: String,
                correlationId: UUID,
            ): PrintScriptService.Output {
                val content = inputStream.reader().readText()
                return PrintScriptService.Output("$content // formatted")
            }

            override fun lint(
                inputStream: ByteArrayInputStream,
                version: String,
                userId: String,
                correlationId: UUID,
            ): List<PrintScriptService.SCAOutput> {
                return listOf(
                    PrintScriptService.SCAOutput("Rule1", 1, "Description1"),
                    PrintScriptService.SCAOutput("Rule2", 2, "Description2"),
                )
            }
        }

    // Instancia del servicio con la dependencia simulada
    private val defaultRedisService = DefaultRedisService(snippetService)

    @Test
    fun `test formatSnippet`() {
        val snippet = Snippet("1", "let x = 42;", "user1", UUID.randomUUID())
        val result = defaultRedisService.formatSnippet(snippet)

        val expectedContent = "let x = 42; // formatted"
        assertEquals(expectedContent, result.content)
    }

    @Test
    fun `test lintSnippet`() {
        val snippet = Snippet("1", "let x = 42;", "user1", UUID.randomUUID())
        val result = defaultRedisService.lintSnippet(snippet)

        val expectedContent =
            """
            Rule: Rule1, Line: 1, Description: Description1
            Rule: Rule2, Line: 2, Description: Description2
            """.trimIndent()

        assertEquals(expectedContent, result.content)
    }
}

// Implementaciones simuladas de las interfaces y clases necesarias
interface PrintScriptService {
    data class Output(val content: String)

    data class SCAOutput(val rule: String, val line: Int, val description: String)

    fun format(
        id: String,
        inputStream: ByteArrayInputStream,
        version: String,
        userId: String,
        correlationId: UUID,
    ): Output

    fun lint(
        inputStream: ByteArrayInputStream,
        version: String,
        userId: String,
        correlationId: UUID,
    ): List<SCAOutput>
}

data class Snippet(val id: String, val content: String, val userId: String, val correlationID: UUID)

class DefaultRedisService(private val snippetService: PrintScriptService) {
    fun formatSnippet(snippet: Snippet): PrintScriptService.Output {
        val inputStream = ByteArrayInputStream(snippet.content.toByteArray())
        return snippetService.format(snippet.id, inputStream, "1.1", snippet.userId, snippet.correlationID)
    }

    fun lintSnippet(snippet: Snippet): PrintScriptService.Output {
        val inputStream = ByteArrayInputStream(snippet.content.toByteArray())
        val lintResults = snippetService.lint(inputStream, "1.1", snippet.userId, snippet.correlationID)
        val content = lintResults.joinToString("\n") { "Rule: ${it.rule}, Line: ${it.line}, Description: ${it.description}" }
        return PrintScriptService.Output(content)
    }
}
