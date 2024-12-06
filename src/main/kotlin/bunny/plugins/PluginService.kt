@file:Suppress("UnstableApiUsage")

package bunny.plugins

import com.intellij.ide.plugins.PluginEnabler
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.extensions.PluginId

@Service
class PluginService {

    private var dependencies: List<String> =
        PluginManagerCore.getPlugin(PluginId.findId("cc.implicated.intellij.plugins.bunny"))?.let {
            it.dependencies.map { depend -> depend.pluginId.idString }
        } ?: throw IllegalStateException("Plugin not found")

    fun disablePlugins() {
        val productCode = ApplicationInfo.getInstance().build.productCode
        PluginManagerCore.loadedPlugins.mapNotNull { plugin ->
            val pluginId = plugin.pluginId.idString
            thisLogger().info("zylog#PluginService ==> plugin:{$pluginId}")

            if (checkEssential(productCode, pluginId)) {
                null
            } else {
                PluginId.getId(pluginId)
            }
        }.let {
            val pluginService = PluginEnabler.getInstance()
            pluginService.disableById(it.toSet())
        }
    }

    private fun checkEssential(productCode: String, plugin: String): Boolean {
        return essentialPluginsMap.getOrDefault(productCode, listOf())
            .plus(dependencies)
            .plus(
                listOf(
                    "cc.implicated.intellij.plugins.bunny",
                    "Git4Idea",
                    "org.jetbrains.plugins.terminal",
                )
            )
            .contains(plugin)
    }

    /**
     * /app/Contents/lib/product.jar/idea.ApplicationInfo.xml
     */
    private val essentialPluginsMap: MutableMap<String, MutableList<String>> = mutableMapOf(
        "DB" to mutableListOf(
            // 必须且需要指定
            "intellij.charts",
            "com.intellij.platform.images",
            "com.intellij.database",
            "com.intellij.database.ide",
            "intellij.grid.impl",
            "intellij.grid.core.impl",

            // 自己追加
            "com.intellij.plugins.datagrip.solarized.colorscheme",
        ),
        "IU" to mutableListOf(
            // 必须且需要指定
            "com.intellij.java",
            "com.intellij.java.ide",
            "com.intellij.modules.json",

            "com.intellij.analysis.pwa",
            "com.intellij.analysis.pwa.java",
        ),
        "PY" to mutableListOf(
            // 必须且需要指定
            "intellij.python.scientific",
            "com.jetbrains.pycharm.pro.customization",
            "com.intellij.platform.images",
            "intellij.charts",
            "intellij.grid.impl",
            "intellij.grid.core.impl",

            // 必须且可不指定
            "PythonCore",
            "com.intellij",
            "org.toml.lang",
            "com.intellij.modules.json",
            "Pythonid",
            "intellij.jupyter",
            "com.intellij.notebooks.core",
        ),
        "WS" to mutableListOf(
            // 必须且需要指定
            "com.intellij.platform.images",
            "com.intellij.css",
            "JavaScript",
            "com.intellij.modules.json",
        ),
        "GO" to mutableListOf(
            // 必须且需要指定
            "org.jetbrains.plugins.go",
            "org.jetbrains.plugins.go.ide",
            "com.intellij.modules.json",

            // 自己追加
            "org.jetbrains.plugins.go-template",
        )
    )
}