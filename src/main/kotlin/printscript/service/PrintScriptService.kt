package printscript.service

import formatter.FormatterPS
import lexer.Lexer
import lexer.TokenMapper
import linter.Linter
import org.springframework.stereotype.Service
import parser.Parser
import printscript.interfaces.LanguageService
import printscript.model.Output
import printscript.model.SCAOutput
import java.io.InputStream
import java.util.UUID

@Service
class PrintScriptService(
    private val tokenMapper: TokenMapper,
    private val parser: Parser,
    private val linter: Linter,
    private val formatter: FormatterPS,
) : LanguageService {
    override fun runScript(
        input: InputStream,
        version: String,
    ): Output {
        // Paso 1: Análisis léxico
        val lexer = Lexer(tokenMapper)
        val tokens = lexer.execute(input.bufferedReader().readText())

        // Paso 2: Análisis sintáctico
        val script = parser.execute(tokens)

        // Paso 3: Ejecución del script
        // Aquí asumimos que el árbol sintáctico tiene un método `evaluate` para ejecutar el código

        return Output(script.toString())
    }

    override fun format(
        snippetId: String,
        input: InputStream,
        version: String,
        userId: String,
        correlationId: UUID,
    ): Output {
        val formattedScript = formatter.format(input.bufferedReader().readText())
        return Output(formattedScript)
    }

    override fun lint(
        input: InputStream,
        version: String,
        userId: String,
        correlationId: UUID,
    ): List<SCAOutput> {
        val trees = parser.execute(Lexer(tokenMapper).execute(input.bufferedReader().readText()))
        val lintResults = linter.check(trees)
        return lintResults.getBrokenRules().map { brokenRule ->
            SCAOutput(
                description = brokenRule.ruleDescription,
                lineNumber = brokenRule.errorPosition.row,
                ruleBroken = brokenRule.ruleDescription,
            )
        }
    }

    override fun test(
        input: String,
        output: List<String>,
        snippet: String,
        envVars: String,
    ): String {
        // Paso 1: Crear un InputStream desde el string de entrada
        val inputStream = input.byteInputStream()

        // Paso 2: Ejecutar el script y obtener el resultado
        val executionOutput = runScript(inputStream, version = "1.0")

        // Paso 3: Verificar si el resultado coincide con la salida esperada
        val executionResult = executionOutput.string
        return if (output.contains(executionResult)) {
            "Test passed"
        } else {
            "Test failed: expected ${output.joinToString(", ")} but got $executionResult"
        }
    }
}
