package bunny.plugins.atom.provider

import cache.AppCache
import com.intellij.ide.IconProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import icons.Icons
import javax.swing.Icon

class AtomIconProvider : IconProvider(), DumbAware {
    /**
     * @see com.intellij.ide.projectView.impl.CompoundIconProvider
     * @see com.intellij.util.PsiIconUtil.getProvidersIcon
     * @see com.intellij.ui.svg.readAttributes
     */
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        val regex =
            when (element) {
                is PsiDirectory -> {
                    Icons.folders
                        .filter { it.match(element) }
                        .maxByOrNull { it.priority }
                }

                is PsiFile -> {
                    Icons.files
                        .filter { it.match(element) }
                        .maxByOrNull { it.priority }
                }

                else -> null
            }
        return regex?.let {
            AppCache.instance.iconCache.getOrPut(it.icon) {
                Icons.getIcon(it.icon)
            }
        }
    }
}