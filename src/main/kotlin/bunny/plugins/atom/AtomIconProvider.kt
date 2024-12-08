package bunny.plugins.atom

import com.intellij.ide.IconProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import icons.AtomIcons
import javax.swing.Icon

class AtomIconProvider : IconProvider(), DumbAware {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        val regex =
            when (element) {
                is PsiDirectory -> {
                    AtomIcons.folders
                        .filter { it.match(element) }
                        .maxByOrNull { it.priority }
                }

                is PsiFile -> {
                    AtomIcons.files
                        .filter { it.match(element) }
                        .maxByOrNull { it.priority }
                }

                else -> null
            }
        return regex?.let {
            IconLoader.getIcon(it.icon, AtomIcons.javaClass)
        }
    }
}