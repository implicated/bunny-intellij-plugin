package bunny.theme

import com.intellij.codeInsight.CodeInsightSettings
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzerSettings
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.editor.EditorSettings
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.ex.EditorSettingsExternalizable


@Service
class EditorService {
    private val schemeName: String = "_@user_bunny-dark-native"

    fun setEditorOptions() {
        setEditorColorScheme()
        setEditorOption()
    }

    // /options/editor.xml
    private fun setEditorOption() {
        val editorSettings = EditorSettingsExternalizable.getInstance()
        // Settings | Editor | General | Appearance
        editorSettings.lineNumeration = EditorSettings.LineNumerationType.RELATIVE
        editorSettings.isLineNumbersShown = true
        editorSettings.isWhitespacesShown = true
        editorSettings.isIndentGuidesShown = true
        editorSettings.isDocCommentRenderingEnabled = true
        // Show hard wrap and visual guides （configured in Code Style options）
        editorSettings.isRightMarginShown = true
        editorSettings.isFoldingOutlineShown = false
        editorSettings.isFoldingOutlineShownOnlyOnHover = false
        editorSettings.isBreadcrumbsShown = false
        editorSettings.setGutterIconsShown(true)
        val daemonCodeAnalyzerSettings = DaemonCodeAnalyzerSettings.getInstance()
        daemonCodeAnalyzerSettings.SHOW_METHOD_SEPARATORS = true

        // Settings | Editor | General
        editorSettings.isAdditionalPageAtBottom = true

        // Settings | Editor | Code Editing
        editorSettings.isShowQuickDocOnMouseOverElement = false


        val codeInsightSettings = CodeInsightSettings.getInstance()
        codeInsightSettings.completionCaseSensitive = CodeInsightSettings.NONE
        codeInsightSettings.AUTO_POPUP_JAVADOC_INFO = true
    }

    private fun setEditorColorScheme() {
        val colorsManager = EditorColorsManager.getInstance()
        val schemeToSet = colorsManager.allSchemes.find { it.name == schemeName }

        if (schemeToSet != null) {
            colorsManager.setGlobalScheme(schemeToSet)
            thisLogger().debug("Editor color scheme set to: ${schemeToSet.name}")
        } else {
            thisLogger().debug("Editor color scheme '$schemeName' not found.")
        }
    }
}