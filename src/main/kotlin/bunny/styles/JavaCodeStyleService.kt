package bunny.styles

import com.intellij.application.options.CodeStyle
import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class JavaCodeStyleService {
    companion object {
        const val PLUGIN_ID = "com.intellij.java"
    }


    fun commentStyle(project: Project) {
        val codeStyleSettings = CodeStyle.getSettings(project)
        val commonSettings = codeStyleSettings.getCommonSettings(JavaLanguage.INSTANCE)
        commonSettings.LINE_COMMENT_AT_FIRST_COLUMN = false
        // Add a space at line comment start
        commonSettings.LINE_COMMENT_ADD_SPACE = true
        // Enforce on reformat
        commonSettings.LINE_COMMENT_ADD_SPACE_ON_REFORMAT = true
        // Block comment at first column
        commonSettings.BLOCK_COMMENT_AT_FIRST_COLUMN = true
        // Add spaces around block comments
        commonSettings.BLOCK_COMMENT_ADD_SPACE = false

        commonSettings.WRAP_LONG_LINES = true
    }
}
