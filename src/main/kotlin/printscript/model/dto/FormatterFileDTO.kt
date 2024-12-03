package printscript.model.dto

data class FormatterFileDTO(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundEquals: Boolean,
    val lineBreakPrintln: Int,
    val conditionalIndentation: Int,
)
