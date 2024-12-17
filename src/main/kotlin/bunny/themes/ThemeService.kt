@file:Suppress("UnstableApiUsage")

package bunny.themes

import bunny.plugins.atom.patcher.AtomIconSvgPatcher
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.laf.LafManagerImpl
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.util.SVGLoader

@Service
class ThemeService {
    private val themeName: String = "bunny-dark-native"

    fun setTheme() {
        // 获取 LafManager 实例
        val lafManager = LafManager.getInstance() as LafManagerImpl

        // 查找指定名称的主题
        lafManager.installedThemes
            .find { it.name == themeName }
            ?.let {
                lafManager.setCurrentLookAndFeel(it, false)
            }.also {
                SVGLoader.colorPatcherProvider = service<AtomIconSvgPatcher>()
                lafManager.updateUI() // 更新 UI 以应用更改
            }
    }
}