package bunny.init

import bunny.plugins.PluginService
import bunny.settings.EditorService
import bunny.settings.GeneralService
import bunny.settings.KeymapService
import bunny.templates.LiveTemplateService
import bunny.themes.ThemeService
import bunny.themes.UIService
import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.registry.Registry
import icons.Icons

class AppEvent : AppLifecycleListener {
    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        Icons.patchers.forEach { patcher -> IconLoader.installPathPatcher(patcher) }

        // 使用 ApplicationManager.getApplication().invokeLater 确保在 EDT 上执行，非阻塞的
        ApplicationManager.getApplication().invokeLater {
            val config = service<BunnyConfig>().state
            // run only times
            if (config.firstRun) {
                Registry.get("editor.distraction.free.mode").setValue(true)
                Registry.get("ide.browser.jcef.contextMenu.devTools.enabled").setValue(true)
                service<PluginService>().disablePlugins()
                service<KeymapService>().setActiveKeymap()
                service<GeneralService>().setGeneralOption()
                service<ThemeService>().setTheme()
                service<UIService>().setUIOption()
                service<EditorService>().setEditorOptions()
                service<LiveTemplateService>().removeLiveTemplate()

                config.firstRun = false
                // ApplicationManager.getApplication().saveSettings()
                thisLogger().info("zylog#app ==> ${config.firstRun}")
            }
        }
    }
}