class SnippetLintProducerTest {
    /*
    @Test
    @Suppress("UNCHECKED_CAST")
    fun `test publishEvent does not throw exceptions`() = runBlocking {
        // Configurar un ReactiveRedisTemplate vacío
        val redisTemplate = mock(ReactiveRedisTemplate::class.java) as ReactiveRedisTemplate<String, String>
        val streamOps = mock(ReactiveStreamOperations::class.java) as ReactiveStreamOperations<String, String, Snippet>

        `when`(redisTemplate.opsForStream<String, Snippet>()).thenReturn(streamOps)
        `when`(streamOps.add(Mockito.any())).thenReturn(Mono.just(RecordId.of("0-0")))

        // Configurar el SnippetLintProducer
        val streamKey = "lint-stream"
        val producer = SnippetLintProducer(streamKey, redisTemplate)

        // Crear un Snippet de prueba
        val snippet = Snippet("exampleId", "exampleContent", "exampleUserId", UUID.randomUUID())

        // Ejecutar publishEvent y verificar que no arroje excepciones
        assert(producer.publishEvent(snippet) == Unit) // Si no falla, el test pasa.

    }

     */
}
