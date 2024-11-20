package bunny.style

import com.intellij.application.options.CodeStyle
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.KotlinLanguage

@Service(Service.Level.PROJECT)
class KotlinCodeStyleService {
    companion object {
        const val PLUGIN_ID = "org.jetbrains.kotlin"
    }


    fun commentStyle(project: Project) {
        val codeStyleSettings = CodeStyle.getSettings(project)
        val commonSettings = codeStyleSettings.getCommonSettings(KotlinLanguage.INSTANCE)
        commonSettings.LINE_COMMENT_AT_FIRST_COLUMN = false
        commonSettings.LINE_COMMENT_ADD_SPACE = true
        commonSettings.LINE_COMMENT_ADD_SPACE_ON_REFORMAT = true
        commonSettings.BLOCK_COMMENT_AT_FIRST_COLUMN = true
        commonSettings.BLOCK_COMMENT_ADD_SPACE = false
    }
}
