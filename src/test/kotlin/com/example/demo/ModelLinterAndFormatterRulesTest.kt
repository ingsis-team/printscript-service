import org.junit.jupiter.api.Test
import printscript.model.rules.FormatterRules
import printscript.model.rules.LinterRules
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ModelLinterAndFormatterRulesTest {
    @Test
    fun `should create and verify FormatterRules correctly`() {
        val formatterRules =
            FormatterRules(
                userId = "user123",
                spaceBeforeColon = true,
                spaceAfterColon = false,
                spaceAroundEquals = true,
                lineBreakPrintln = 3,
                conditionalIndentation = 4,
            )

        // Verificamos que se hayan asignado correctamente los valores
        assertNotNull(formatterRules.id, "ID should be generated and not null") // ID generado manualmente
        assertEquals("user123", formatterRules.userId)
        assertEquals(true, formatterRules.spaceBeforeColon)
        assertEquals(false, formatterRules.spaceAfterColon)
        assertEquals(true, formatterRules.spaceAroundEquals)
        assertEquals(3, formatterRules.lineBreakPrintln)
        assertEquals(4, formatterRules.conditionalIndentation)
    }

    @Test
    fun `should create and verify LinterRules correctly`() {
        val linterRules =
            LinterRules(
                userId = "user456",
                identifierFormat = "snake_case",
                enablePrintOnly = false,
                enableInputOnly = true,
            )

        // Verificamos que se hayan asignado correctamente los valores
        assertNotNull(linterRules.id, "ID should be generated and not null") // ID generado manualmente
        assertEquals("user456", linterRules.userId)
        assertEquals("snake_case", linterRules.identifierFormat)
        assertEquals(false, linterRules.enablePrintOnly)
        assertEquals(true, linterRules.enableInputOnly)
    }
}
