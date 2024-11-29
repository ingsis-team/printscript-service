package printscript.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import printscript.interfaces.RedisService
import printscript.redis.dto.Snippet
import java.io.ByteArrayInputStream

@Service
class RedisService
    @Autowired
    constructor(
        private val snippetService: PrintScriptService,
    ) : RedisService {
        override fun formatSnippet(snippet: Snippet): Snippet {
            // Llama al método format de PrintScriptService
            val formattedOutput =
                snippetService.format(
                    snippet.id,
                    ByteArrayInputStream(snippet.content.toByteArray()),
                    "1.1",
                    snippet.userId,
                    snippet.correlationID,
                )
            println("Estoy formateando un snippet")

            // Crea un nuevo objeto Snippet con el contenido formateado
            val outputSnippet =
                Snippet(
                    snippet.id,
                    formattedOutput.string,
                    snippet.userId,
                    snippet.correlationID,
                )
            println(formattedOutput.string)

            // Actualiza el bucket con el contenido formateado
            return outputSnippet
        }

        override fun lintSnippet(snippet: Snippet): Snippet {
            println("Estoy linteando un snippet")

            // Llama al método lint de PrintScriptService
            val lintResults =
                snippetService.lint(
                    ByteArrayInputStream(snippet.content.toByteArray()),
                    "1.1",
                    snippet.userId,
                    snippet.correlationID,
                )

            // Extrae las reglas rotas como texto
            val brokenRules =
                lintResults.joinToString("\n") { scaOutput ->
                    "Rule: ${scaOutput.ruleBroken}, Line: ${scaOutput.lineNumber}, Description: ${scaOutput.description}"
                }

            // Crea un nuevo Snippet con las reglas rotas
            val outputSnippet = Snippet(snippet.id, brokenRules, snippet.userId, snippet.correlationID)
            return outputSnippet
        }
    }
