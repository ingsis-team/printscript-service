package printscript.service

import printscript.model.Output
import printscript.model.SCAOutput
import printscript.service.interfaces.LanguageService
import java.io.InputStream
import java.util.UUID

class PrintScriptService : LanguageService {
    override fun runScript(
        input: InputStream,
        version: String,
    ): Output {
        TODO("Not yet implemented")
    }

    override fun format(
        snippetId: String,
        input: InputStream,
        version: String,
        userId: String,
        correlationId: UUID,
    ): Output {
        TODO("Not yet implemented")
    }

    override fun lint(
        input: InputStream,
        version: String,
        userId: String,
        correlationId: UUID,
    ): List<SCAOutput> {
        TODO("Not yet implemented")
    }

    override fun test(
        input: String,
        output: List<String>,
        snippet: String,
        envVars: String,
    ): String {
        TODO("Not yet implemented")
    }
}
