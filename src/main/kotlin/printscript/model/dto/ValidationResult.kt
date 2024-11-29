package printscript.model.dto

data class ValidationResult(
    val isValid: Boolean,
    val rule: String,
    val line: Int,
    val column: Int,
)
