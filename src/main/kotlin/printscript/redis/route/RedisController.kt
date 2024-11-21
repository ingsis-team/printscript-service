package printscript.redis.route

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import printscript.model.dto.FormatterRulesFileDTO
import printscript.model.dto.LinterRulesFileDTO
import printscript.redis.dto.ChangeRulesDTO
import printscript.redis.dto.Snippet
import printscript.redis.producer.SnippetFormatterProducer
import printscript.redis.producer.SnippetLintProducer
import printscript.service.FormatterRulesService
import printscript.service.LinterRulesService

@RestController
@RequestMapping("redis")
class RedisController
    @Autowired
    constructor(
        private val formatProducer: SnippetFormatterProducer,
        private val lintProducer: SnippetLintProducer,
        private val formatterService: FormatterRulesService,
        private val linterRulesService: LinterRulesService,
    ) {
        @PutMapping("/format")
        suspend fun changeAndFormatRules(
            @RequestBody data: ChangeRulesDTO,
        ) {
            println("changerulesdto: ${data.rules.forEach(::println)}")

            val spaceBeforeColon = data.rules.find { it.name == "SpaceBeforeColon" }?.value as? Boolean ?: false
            val spaceAfterColon = data.rules.find { it.name == "SpaceAfterColon" }?.value as? Boolean ?: false
            val spaceAroundEquals = data.rules.find { it.name == "SpaceAroundEquals" }?.value as? Boolean ?: false
            val lineBreak = data.rules.find { it.name == "LineBreak" }?.value as? Int ?: 0
            val lineBreakPrintln = data.rules.find { it.name == "LineBreakPrintln" }?.value as? Int ?: 0
            val conditionalIndentation = data.rules.find { it.name == "ConditionalIndentation" }?.value as? Int ?: 0

            val formatterDto =
                FormatterRulesFileDTO(
                    data.userId,
                    spaceBeforeColon,
                    spaceAfterColon,
                    spaceAroundEquals,
                    lineBreak,
                    lineBreakPrintln,
                    conditionalIndentation,
                )
            println(
                "formatterDto: userId=${formatterDto.userId}, spaceBeforeColon=${formatterDto.spaceBeforeColon}," +
                    " spaceAfterColon=${formatterDto.spaceAfterColon}, " +
                    "spaceAroundEquals=${formatterDto.spaceAroundEquals}, lineBreak=${formatterDto.lineBreak}," +
                    " lineBreakPrintln=${formatterDto.lineBreakPrintln}," +
                    " conditionalIndentation=${formatterDto.conditionalIndentation}",
            )

            formatterService.updateFormatterRules(formatterDto, data.userId)
            println("Rules updated")
            data.snippets.forEach {
                val snippet = Snippet(it.snippetId, it.input, data.userId, data.correlationId)
                formatProducer.publishEvent(snippet)
            }
            println("Rules published")
        }

        @PutMapping("lint")
        suspend fun changeAndLintRules(
            @RequestBody data: ChangeRulesDTO,
        ) {
            val identifierFormat = data.rules.find { it.name == "identifierFormat" }?.value as? String ?: ""
            val enablePrintOnly = data.rules.find { it.name == "enablePrintOnly" }?.value as? Boolean ?: false
            val enableInputOnly = data.rules.find { it.name == "enableInputOnly" }?.value as? Boolean ?: false
            val enablePrintOnlySnakeCase = data.rules.find { it.name == "enable_print_only" }?.value as? Boolean ?: false
            val enableInputOnlySnakeCase = data.rules.find { it.name == "enable_input_only" }?.value as? Boolean ?: false

            val linterDto =
                LinterRulesFileDTO(
                    data.userId,
                    identifierFormat,
                    enablePrintOnly,
                    enableInputOnly,
                    enablePrintOnlySnakeCase,
                    enableInputOnlySnakeCase,
                )
            linterRulesService.updateLinterRules(linterDto, data.userId)
            data.snippets.map {
                val snippet = Snippet(it.snippetId, it.input, data.userId, data.correlationId)
                lintProducer.publishEvent(snippet)
            }
        }
    }
