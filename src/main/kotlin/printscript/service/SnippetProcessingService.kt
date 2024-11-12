package printscript.service

import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext
import printscript.service.interfaces.LanguageService

@Service
class SnippetProcessingService(private val applicationContext: WebApplicationContext) {
    fun selectService(language: String): LanguageService {
        return when (language) {
            "printscript" -> applicationContext.getBean(PrintScriptService::class.java)
            else -> throw IllegalArgumentException("Unsupported language $language")
        }
    }
}
