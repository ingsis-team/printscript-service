package printscript.model.dto

data class FormatterRulesFileDTO(
    val userId: String,
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundEquals: Boolean,
    val lineBreakPrintln: Int,
    val conditionalIndentation: Int,
)
