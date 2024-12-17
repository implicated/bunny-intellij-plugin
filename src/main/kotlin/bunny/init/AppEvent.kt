package bunny.init

import bunny.plugins.PluginService
import bunny.settings.EditorService
import bunny.settings.GeneralService
import bunny.settings.KeymapService
import bunny.templates.LiveTemplateService
import bunny.themes.*
import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.registry.Registry
import icons.Icons
import org.jetbrains.kotlin.utils.addToStdlib.ifTrue

class AppEvent : AppLifecycleListener {
    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        Icons.patchers.forEach { patcher -> IconLoader.installPathPatcher(patcher) }

        // 使用 ApplicationManager.getApplication().invokeLater 确保在 EDT 上执行，非阻塞的
        ApplicationManager.getApplication().invokeLater {
            Registry.get("editor.distraction.free.mode").setValue(true)
            service<KeymapService>().setActiveKeymap()
            service<GeneralService>().setGeneralOption()
            service<ThemeService>().setTheme()
            service<UIService>().setUIOption()
            service<EditorService>().setEditorOptions()
            service<LiveTemplateService>().removeLiveTemplate()

            val config = service<BunnyConfig>().state
            config.firstRun.ifTrue {
                service<PluginService>().disablePlugins()
                config.firstRun = false
            }
        }
    }
}