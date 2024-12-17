package bunny.settings

import com.intellij.ide.GeneralSettings
import com.intellij.openapi.components.Service
import com.intellij.openapi.updateSettings.impl.UpdateSettings
import com.intellij.util.net.HttpConfigurable


@Service
class GeneralService {

    fun setGeneralOption() {
        // system settings
        val generalService = GeneralSettings.getInstance()
        generalService.isConfirmExit = false
        generalService.confirmOpenNewProject = GeneralSettings.Companion.OPEN_PROJECT_NEW_WINDOW

        // HTTP Proxy
        val httpConfigurable = HttpConfigurable.getInstance()
        httpConfigurable.USE_HTTP_PROXY = true
        httpConfigurable.PROXY_HOST = "127.0.0.1"
        httpConfigurable.PROXY_PORT = 6152
        httpConfigurable.PROXY_EXCEPTIONS = "*.iqihang.com,localhost,*.aliyuncs.com"
        httpConfigurable.CHECK_CONNECTION_URL = "http://www.google.com"

        // Updates
        val updateSettings = UpdateSettings.getInstance().state
        updateSettings.isCheckNeeded = false
        updateSettings.isObsoleteCustomRepositoriesCleanNeeded = false
        updateSettings.isPluginsCheckNeeded = false
        updateSettings.isShowWhatsNewEditor = false
        updateSettings.isThirdPartyPluginsAllowed = true
    }
}
