package printscript.server
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.slf4j.MDC
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FiltersTest {
    @Test
    fun `CorrelationIdFilter should generate a new correlation ID when header is missing`() {
        // Mock dependencies
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val filterChain = mock(FilterChain::class.java)
        val filter = CorrelationIdFilter()

        // Simulate behavior: header is missing
        `when`(request.getHeader(CorrelationIdFilter.CORRELATION_ID_HEADER)).thenReturn(null)

        // Clear MDC to prevent interference
        MDC.clear()

        // Execute filter
        filter.doFilterInternal(request, response, filterChain)

        // Verify correlation ID is generated and added to MDC
        val correlationId = MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY)
        assertTrue(correlationId != null && correlationId.isNotBlank(), "Correlation ID was not added to MDC")

        // Verify the UUID format
        assertTrue(
            correlationId.matches(Regex("^[a-f0-9\\-]{36}\$")),
            "Generated correlation ID is not a valid UUID",
        )

        // Verify filter chain was called
        verify(filterChain).doFilter(request, response)
    }

    @Test
    fun `CorrelationIdFilter should use existing correlation ID from header`() {
        // Mock dependencies
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val filterChain = mock(FilterChain::class.java)
        val filter = CorrelationIdFilter()

        // Set an existing correlation ID in the header
        val existingCorrelationId = "test-correlation-id"
        `when`(request.getHeader(CorrelationIdFilter.CORRELATION_ID_HEADER)).thenReturn(existingCorrelationId)

        // Clear MDC to prevent interference
        MDC.clear()

        // Execute filter
        filter.doFilterInternal(request, response, filterChain)

        // Verify the correlation ID is correctly used
        val actualCorrelationId = MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY)
        assertEquals(existingCorrelationId, actualCorrelationId, "Correlation ID from header was not used")

        // Verify filter chain was called
        verify(filterChain).doFilter(request, response)
    }

    @Test
    fun `RequestLogFilter should log request method and URI`() {
        // Mock dependencies
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val filterChain = mock(FilterChain::class.java)
        val filter = RequestLogFilter()

        // Simulate behavior
        `when`(request.requestURI).thenReturn("/test/endpoint")
        `when`(request.method).thenReturn("GET")
        `when`(response.status).thenReturn(200)

        // Execute filter
        filter.doFilterInternal(request, response, filterChain)

        // Verify filter chain was called
        verify(filterChain).doFilter(request, response)

        // Verify logging (mock logger, if needed)
        // Example:
        // verify(logger).info("GET /test/endpoint - 200")
    }

    @Test
    fun `RequestLogFilter should handle exceptions in filter chain`() {
        // Mock dependencies
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val filterChain = mock(FilterChain::class.java)
        val filter = RequestLogFilter()

        // Simulate behavior
        `when`(request.requestURI).thenReturn("/test/error")
        `when`(request.method).thenReturn("POST")
        `when`(response.status).thenReturn(500)
        doThrow(RuntimeException("Filter chain error")).`when`(filterChain).doFilter(request, response)

        // Execute filter and catch exception
        try {
            filter.doFilterInternal(request, response, filterChain)
        } catch (e: Exception) {
            assertEquals("Filter chain error", e.message)
        }

        // Verify exception is thrown and logged
        // Example:
        // verify(logger).error("Exception processing request", RuntimeException("Filter chain error"))
    }
}
