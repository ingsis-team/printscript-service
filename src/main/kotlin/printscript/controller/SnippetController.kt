package printscript.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import printscript.model.Output
import printscript.model.SCAOutput
import printscript.model.dto.SnippetDTO
import printscript.model.dto.SnippetOutputDTO
import printscript.model.dto.TestDTO
import printscript.redis.dto.Rule
import printscript.service.FormatterRulesService
import printscript.service.LinterRulesService
import printscript.service.SnippetProcessingService
import java.io.ByteArrayInputStream
import java.util.UUID

@RestController
class SnippetController(
    private val snippetProcessingService: SnippetProcessingService,
    private val linterRulesService: LinterRulesService,
    private val formaterRulesService: FormatterRulesService,
) {
    @PostMapping("/run")
    fun runSnippet(
        @RequestBody snippetRunnerDTO: SnippetDTO,
    ): ResponseEntity<SnippetOutputDTO> {
        val languageService = snippetProcessingService.selectService(snippetRunnerDTO.language)
        val inputStream = ByteArrayInputStream(snippetRunnerDTO.input.toByteArray())
        val output: Output = languageService.runScript(inputStream, snippetRunnerDTO.version)
        val snippetOutput = SnippetOutputDTO(output.string, snippetRunnerDTO.correlationId, snippetRunnerDTO.snippetId)
        return ResponseEntity(snippetOutput, HttpStatus.OK)
    }

    @PostMapping("/format")
    fun formatSnippet(
        @RequestBody snippetRunnerDTO: SnippetDTO,
    ): ResponseEntity<SnippetOutputDTO> {
        val languageService = snippetProcessingService.selectService(snippetRunnerDTO.language)
        val inputStream = ByteArrayInputStream(snippetRunnerDTO.input.toByteArray())
        val output =
            languageService.format(
                snippetRunnerDTO.snippetId,
                inputStream,
                snippetRunnerDTO.version,
                snippetRunnerDTO.userId,
                snippetRunnerDTO.correlationId,
            )
        val snippetOutput = SnippetOutputDTO(output.string, snippetRunnerDTO.correlationId, snippetRunnerDTO.snippetId)
        return ResponseEntity(snippetOutput, HttpStatus.OK)
    }

    @PostMapping("/lint")
    fun runLinter(
        @RequestBody snippetRunnerDTO: SnippetDTO,
    ): ResponseEntity<List<SCAOutput>> {
        val languageService = snippetProcessingService.selectService(snippetRunnerDTO.language)
        val inputStream = ByteArrayInputStream(snippetRunnerDTO.input.toByteArray())
        val output =
            languageService.lint(
                inputStream,
                snippetRunnerDTO.version,
                snippetRunnerDTO.userId,
                snippetRunnerDTO.correlationId,
            )
        return ResponseEntity(output, HttpStatus.OK)
    }

    @GetMapping("/format/{userId}")
    fun getLinterRules(
        @PathVariable userId: String,
        @RequestHeader("Correlation-id") correlationId: UUID,
    ): ResponseEntity<List<Rule>> {
        val formatterRules = formaterRulesService.getFormatterRulesByUserId(userId, correlationId)
        val rulesList = mutableListOf<Rule>()

        rulesList.add(Rule(id = "1", name = "spaceBeforeColon", isActive = true, value = formatterRules.spaceBeforeColon))
        rulesList.add(Rule(id = "2", name = "spaceAfterColon", isActive = true, value = formatterRules.spaceAfterColon))
        rulesList.add(Rule(id = "3", name = "spaceAroundEquals", isActive = true, value = formatterRules.spaceAroundEquals))
        rulesList.add(Rule(id = "4", name = "lineBreak", isActive = true, value = formatterRules.lineBreak))
        rulesList.add(Rule(id = "5", name = "lineBreakPrintln", isActive = true, value = formatterRules.lineBreakPrintln))
        rulesList.add(Rule(id = "6", name = "conditionalIndentation", isActive = true, value = formatterRules.conditionalIndentation))

        return ResponseEntity.ok(rulesList)
    }

    @GetMapping("/lint/{userId}")
    fun getFormatterRules(
        @PathVariable userId: String,
        @RequestHeader("Correlation-id") correlationId: UUID,
    ): ResponseEntity<List<Rule>> {
        val linterRules = linterRulesService.getLinterRulesByUserId(userId, correlationId)
        val rulesList = mutableListOf<Rule>()

        rulesList.add(Rule(id = "1", name = "identifierFormat", isActive = true, value = linterRules.identifierFormat))
        rulesList.add(Rule(id = "2", name = "enablePrintOnly", isActive = true, value = linterRules.enablePrintOnly))
        rulesList.add(Rule(id = "3", name = "enableInputOnly", isActive = true, value = linterRules.enableInputOnly))

        return ResponseEntity.ok(rulesList)
    }

    @PostMapping("/test")
    fun makeTest(
        @RequestBody testDto: TestDTO,
    ): ResponseEntity<String> {
        val languageService = snippetProcessingService.selectService("printscript")
        val result = languageService.test(testDto.input, testDto.output, testDto.snippet, testDto.envVars)
        return ResponseEntity(result, HttpStatus.OK)
    }
}
