package bunny.init

import bunny.plugins.PluginService
import bunny.template.LiveTemplateService
import bunny.theme.*
import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.util.registry.Registry
import org.jetbrains.kotlin.utils.addToStdlib.ifFalse

class AppEvent : AppLifecycleListener {
    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        // 使用 ApplicationManager.getApplication().invokeLater 确保在 EDT 上执行
        ApplicationManager.getApplication().invokeLater {
            Registry.get("editor.distraction.free.mode").setValue(true)

            val state = service<BunnyConfig>().state
            thisLogger().info("zylog#AppEvent ==> initial:{${state}}")
            state.initial.ifFalse {
                service<PluginService>().disablePlugins()
                service<KeymapService>().setActiveKeymap()
                service<GeneralService>().setGeneralOption()
                service<ThemeService>().setTheme()
                service<UIService>().setUIOption()
                service<EditorService>().setEditorOptions()
                service<LiveTemplateService>().removeLiveTemplate()
                state.initial = true
            }
        }
    }
}