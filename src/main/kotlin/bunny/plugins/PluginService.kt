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
            thisLogger().info("zylog#loaded ==> ${productCode}:$pluginId")

            if (checkEssential(productCode, pluginId)) {
                null
            } else {
                PluginId.getId(pluginId)
            }
        }.let {
            val pluginService = PluginEnabler.getInstance()
            it.forEach { pluginId ->
                thisLogger().info("zylog#disable ==> ${productCode}:${pluginId.idString}")
            }
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
                    // Local AI/ML Tool
                    "com.intellij.completion.ml.ranking",
                    "com.intellij.marketplace.ml",
                    "com.intellij.ml.inline.completion",
                    "com.intellij.searcheverywhere.ml",
                    "org.jetbrains.completion.full.line",
                    // other
                    "org.intellij.intelliLang",
                    "org.jetbrains.plugins.terminal",
                    "org.jetbrains.plugins.textmate",
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
            "intellij.grid.impl",
            "intellij.grid.core.impl",
            "com.intellij",
            "com.intellij.platform.images",
            "com.intellij.database",
            "com.intellij.database.ide",

            // 自己追加
            "org.intellij.plugins.markdown",
            "com.intellij.modules.json",
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
            "intellij.charts",
            "intellij.grid.impl",
            "intellij.grid.core.impl",
            "com.intellij",
            "com.intellij.platform.images",

            "PythonCore",
            "Pythonid",
            "intellij.jupyter",
            "intellij.python.scientific",
            "com.intellij.notebooks.core",
            "com.jetbrains.pycharm.pro.customization",
            "org.toml.lang",
            "com.intellij.modules.json",
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