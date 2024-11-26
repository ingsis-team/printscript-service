package printscript.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import printscript.model.Output
import printscript.model.dto.SnippetDTO
import printscript.model.dto.SnippetOutputDTO
import printscript.model.dto.TestDTO
import printscript.service.SnippetProcessingService
import java.io.ByteArrayInputStream

@RestController
class SnippetProcessingController(private val snippetProcessingService: SnippetProcessingService) {
    @PostMapping("/run")
    fun runSnippet(
        @RequestBody snippetDto: SnippetDTO,
    ): ResponseEntity<SnippetOutputDTO> {
        val languageService = snippetProcessingService.selectService(snippetDto.language)
        val inputStream = ByteArrayInputStream(snippetDto.input.toByteArray())
        val output: Output = languageService.runScript(inputStream, snippetDto.version)
        val snippetOutput = SnippetOutputDTO(output.string, snippetDto.correlationId, snippetDto.snippetId)
        return ResponseEntity.status(HttpStatus.OK).body(snippetOutput)
    }

    @PostMapping("/format")
    fun formatSnippet(
        @RequestBody snippetDto: SnippetDTO,
    ): ResponseEntity<SnippetOutputDTO> {
        val languageService = snippetProcessingService.selectService(snippetDto.language)
        val inputStream = ByteArrayInputStream(snippetDto.input.toByteArray())
        val output: Output =
            languageService.format(
                snippetDto.snippetId,
                inputStream,
                snippetDto.version,
                snippetDto.userId,
                snippetDto.correlationId,
            )
        val snippetOutput = SnippetOutputDTO(output.string, snippetDto.correlationId, snippetDto.snippetId)
        return ResponseEntity.status(HttpStatus.OK).body(snippetOutput)
    }

    @PostMapping("/lint")
    fun lintSnippet(
        @RequestBody snippetDto: SnippetDTO,
    ): ResponseEntity<SnippetOutputDTO> {
        val languageService = snippetProcessingService.selectService(snippetDto.language)
        val inputStream = ByteArrayInputStream(snippetDto.input.toByteArray())
        val output = languageService.lint(inputStream, snippetDto.version, snippetDto.userId, snippetDto.correlationId)
        val brokenRules: MutableList<String> = output.flatMap { it.getBrokenRules() }.toMutableList()
        val snippetOutput = SnippetOutputDTO(brokenRules.joinToString("\n"), snippetDto.correlationId, snippetDto.snippetId)
        return ResponseEntity.status(HttpStatus.OK).body(snippetOutput)
    }

    @PostMapping("/test")
    fun testSnippet(
        @RequestBody testDto: TestDTO,
    ): ResponseEntity<String> {
        val languageService = snippetProcessingService.selectService("printscript")
        val output: String = languageService.test(testDto.input, testDto.output, testDto.snippet, testDto.envVars)
        return ResponseEntity.status(HttpStatus.OK).body(output)
    }
}
