package bunny.styles

import com.intellij.application.options.CodeStyle
import com.intellij.lang.properties.PropertiesLanguage
import com.intellij.lang.properties.psi.codeStyle.PropertiesCodeStyleSettings
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class PropertiesCodeStyleService {

    companion object {
        const val PLUGIN_ID = "com.intellij.properties"
    }

    fun customStyle(project: Project) {
        val codeStyleSettings = CodeStyle.getSettings(project)

        val commonSettings = codeStyleSettings.getCommonSettings(PropertiesLanguage.INSTANCE)
        commonSettings.LINE_COMMENT_AT_FIRST_COLUMN = false
        commonSettings.LINE_COMMENT_ADD_SPACE = true
        commonSettings.LINE_COMMENT_ADD_SPACE_ON_REFORMAT = true
        commonSettings.BLOCK_COMMENT_AT_FIRST_COLUMN = true
        commonSettings.BLOCK_COMMENT_ADD_SPACE = false

        val customSettings = codeStyleSettings.getCustomSettings(PropertiesCodeStyleSettings::class.java)
        customSettings.KEEP_BLANK_LINES = true
        customSettings.SPACES_AROUND_KEY_VALUE_DELIMITER = true
    }
}
