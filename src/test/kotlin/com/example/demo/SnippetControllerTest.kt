import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.net.http.HttpClient

class SnippetControllerTest {
    private val client = HttpClient.newHttpClient()
    private val baseUrl = "http://localhost:8080" // Cambia al puerto donde está corriendo tu servidor.

    private val objectMapper = jacksonObjectMapper()

//    @Test
//    fun `runSnippet should return output successfully`() {
//        val snippetDTO =
//            SnippetDTO(
//                input = "println('Hello, World!')",
//                language = "printscript",
//                version = "1.0",
//                snippetId = "snippet1",
//                correlationId = UUID.randomUUID(),
//                userId = "user1",
//            )
//
//        val request =
//            HttpRequest.newBuilder()
//                .uri(URI.create("$baseUrl/run"))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(snippetDTO)))
//                .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        assertEquals(200, response.statusCode())
//        val responseBody: SnippetOutputDTO = objectMapper.readValue(response.body())
//        assertEquals(snippetDTO.correlationId, responseBody.correlationId)
//    }
//
//    @Test
//    fun `formatSnippet should return formatted snippet`() {
//        val snippetDTO =
//            SnippetDTO(
//                input = "println( 'Hello, World!' )",
//                language = "printscript",
//                version = "1.0",
//                snippetId = "snippet1",
//                correlationId = UUID.randomUUID(),
//                userId = "user1",
//            )
//
//        val request =
//            HttpRequest.newBuilder()
//                .uri(URI.create("$baseUrl/format"))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(snippetDTO)))
//                .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        assertEquals(200, response.statusCode())
//        val responseBody: SnippetOutputDTO = objectMapper.readValue(response.body())
//        assertEquals(snippetDTO.correlationId, responseBody.correlationId)
//    }
//
//    @Test
//    fun `runLinter should return linter issues`() {
//        val snippetDTO =
//            SnippetDTO(
//                input = "println 'Hello, World!'",
//                language = "printscript",
//                version = "1.0",
//                snippetId = "snippet1",
//                correlationId = UUID.randomUUID(),
//                userId = "user1",
//            )
//
//        val request =
//            HttpRequest.newBuilder()
//                .uri(URI.create("$baseUrl/lint"))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(snippetDTO)))
//                .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        assertEquals(200, response.statusCode())
//        val responseBody: List<*> = objectMapper.readValue(response.body())
//        assertTrue(responseBody.isNotEmpty())
//    }
//
//    @Test
//    fun `getLinterRules should return linter rules`() {
//        val userId = "user1"
//
//        val request =
//            HttpRequest.newBuilder()
//                .uri(URI.create("$baseUrl/lint/$userId"))
//                .GET()
//                .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        assertEquals(200, response.statusCode())
//        val responseBody: List<*> = objectMapper.readValue(response.body())
//        assertTrue(responseBody.isNotEmpty())
//    }
//
//    @Test
//    fun `makeTest should return success result`() {
//        val testDTO =
//            TestDTO(
//                input = "println('Hello, World!')",
//                output = listOf("Hello, World!\n"),
//                snippet = "println('Hello, World!')",
//                envVars = "",
//            )
//
//        val request =
//            HttpRequest.newBuilder()
//                .uri(URI.create("$baseUrl/test"))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(testDTO)))
//                .build()
//
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//
//        assertEquals(200, response.statusCode())
//        assertEquals("success", response.body())
//    }
}
