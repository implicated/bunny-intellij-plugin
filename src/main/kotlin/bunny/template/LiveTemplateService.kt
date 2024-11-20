package bunny.template

import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service
class LiveTemplateService {
    private val removeList: List<String> = listOf("AsciiDoc", "PlantUml")

    fun removeLiveTemplate() {
        ApplicationManager.getApplication().invokeLater {
            val templateSettings = TemplateSettings.getInstance()
            for (template in templateSettings.templates) {
                if (removeList.contains(template.groupName)) {
                    templateSettings.removeTemplate(template)
                }
            }
        }
    }
}