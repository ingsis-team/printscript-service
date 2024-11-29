package printscript.model.dto

data class LinterRulesFileDTO(
    val userId: String,
    val identifier_format: String,
    val enablePrintOnly: Boolean,
    val enableInputOnly: Boolean,
)
