package printscript.model.dto

data class LinterRulesFileDTO(
    val userId: String,
    val identifier_format: String,
    val enablePrintOnly: Boolean,
    val enableInputOnly: Boolean,
    val enable_print_only: Boolean,
    val enable_input_only: Boolean,
)
