package com.example.demo

import org.mockito.Mockito.mock
import printscript.redis.producer.SnippetFormatterProducer
import printscript.redis.producer.SnippetLintProducer
import printscript.redis.route.RedisController
import printscript.service.FormatterRulesService
import printscript.service.LinterRulesService

class RedisControllerTest {
    private val formatProducer = mock(SnippetFormatterProducer::class.java)
    private val lintProducer = mock(SnippetLintProducer::class.java)
    private val formatterService = mock(FormatterRulesService::class.java)
    private val linterRulesService = mock(LinterRulesService::class.java)

    private val controller =
        RedisController(
            formatProducer = formatProducer,
            lintProducer = lintProducer,
            formatterService = formatterService,
            linterRulesService = linterRulesService,
        )

//    @Test
//    fun `test changeAndFormatRules logic`() =
//        runBlocking {
//            val correlationId = UUID.randomUUID()
//            val rules =
//                listOf(
//                    Rule("1", "SpaceBeforeColon", true, true),
//                    Rule("2", "SpaceAfterColon", true, false),
//                    Rule("3", "SpaceAroundEquals", true, true),
//                    Rule("4", "LineBreak", true, 5),
//                    Rule("5", "ConditionalIndentation", true, 3),
//                )
//            val snippets =
//                listOf(
//                    ExecutionDataDTO(correlationId, "snippet1", "kotlin", "1.0", "val x = 42"),
//                    ExecutionDataDTO(correlationId, "snippet2", "java", "1.1", "int y = 42;"),
//                )
//            val data = ChangeRulesDTO("user1", rules, snippets, correlationId)
//
//            controller.changeAndFormatRules(data)
//
//            verify(formatterService).updateFormatterRules(any(), any())
//            verify(formatProducer).publishEvent(any())
//        }

//    @Test
//    fun `test changeAndLintRules logic`() =
//        runBlocking {
//            val correlationId = UUID.randomUUID()
//            val rules =
//                listOf(
//                    Rule("1", "identifierFormat", true, "snake_case"),
//                    Rule("2", "enablePrintOnly", true, true),
//                    Rule("3", "enableInputOnly", true, false),
//                )
//            val snippets =
//                listOf(
//                    ExecutionDataDTO(correlationId, "snippet3", "python", "3.9", "x = 42"),
//                    ExecutionDataDTO(correlationId, "snippet4", "ruby", "2.7", "puts 'hello'"),
//                )
//            val data = ChangeRulesDTO("user2", rules, snippets, correlationId)
//
//            controller.changeAndLintRules(data)
//
//            verify(linterRulesService).updateLinterRules(any(), any())
//            verify(lintProducer).publishEvent(any())
//        }
}
