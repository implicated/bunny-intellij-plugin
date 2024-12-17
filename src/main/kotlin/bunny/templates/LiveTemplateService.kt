package bunny.templates

import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.openapi.components.Service

@Service
class LiveTemplateService {
    private val removeList: List<String> = listOf("AsciiDoc", "PlantUml")

    fun removeLiveTemplate() {
        val templateSettings = TemplateSettings.getInstance()
        for (template in templateSettings.templates) {
            if (removeList.contains(template.groupName)) {
                templateSettings.removeTemplate(template)
            }
        }
    }
}