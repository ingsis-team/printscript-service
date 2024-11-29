package printscript.interfaces

import printscript.model.Output
import printscript.model.SCAOutput
import printscript.model.dto.ValidationResult
import java.io.InputStream
import java.util.UUID

interface LanguageService {
    fun runScript(
        input: InputStream,
        version: String,
    ): Output

    fun format(
        snippetId: String,
        input: InputStream,
        version: String,
        userId: String,
        correlationId: UUID,
    ): Output

    fun lint(
        input: InputStream,
        version: String,
        userId: String,
        correlationId: UUID,
    ): List<SCAOutput>

    fun test(
        input: String,
        output: List<String>,
        snippet: String,
        envVars: String,
    ): String

    fun validate(
        input: String,
        version: String,
    ): ValidationResult
}
