package bunny.theme

import com.intellij.ide.ui.UIDensity
import com.intellij.ide.ui.UISettings
import com.intellij.openapi.components.Service

@Service
class UIService {
    fun setUIOption() {
        val uiSettings = UISettings.getInstance()
        uiSettings.editorTabPlacement = 0
        uiSettings.hideToolStripes = true
        uiSettings.openInPreviewTabIfPossible = true
        uiSettings.showInplaceComments = true
        uiSettings.showNavigationBar = false
        uiSettings.showStatusBar = true
        uiSettings.showTreeIndentGuides = true
        uiSettings.showToolWindowsNumbers = true

        // new ui
        uiSettings.showNewMainToolbar = false
        uiSettings.uiDensity = UIDensity.COMPACT

        // Settings | Advanced Settings | Editor Tab
        uiSettings.openTabsInMainWindow = true
        uiSettings.reuseNotModifiedTabs = true
    }
}