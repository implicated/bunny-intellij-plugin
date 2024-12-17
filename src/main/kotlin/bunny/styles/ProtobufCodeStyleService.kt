package bunny.styles

import com.intellij.application.options.CodeStyle
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.protobuf.lang.PbLanguage

@Service(Service.Level.PROJECT)
class ProtobufCodeStyleService {
    companion object {
        // private val pluginId = "io.kanro.idea.plugin.protobuf"
        const val PLUGIN_ID = "idea.plugin.protoeditor"
    }


    fun commonStyle(project: Project) {
        val codeStyleSettings = CodeStyle.getSettings(project)

        val commonSettings = codeStyleSettings.getCommonSettings(PbLanguage.INSTANCE)
        commonSettings.LINE_COMMENT_AT_FIRST_COLUMN = false
        commonSettings.LINE_COMMENT_ADD_SPACE = true
        commonSettings.LINE_COMMENT_ADD_SPACE_ON_REFORMAT = true
        commonSettings.BLOCK_COMMENT_AT_FIRST_COLUMN = true
        commonSettings.BLOCK_COMMENT_ADD_SPACE = false
        commonSettings.indentOptions?.TAB_SIZE = 2
        commonSettings.indentOptions?.INDENT_SIZE = 2
    }
}
