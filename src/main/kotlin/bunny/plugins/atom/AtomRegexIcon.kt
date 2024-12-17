package bunny.plugins.atom

import bunny.plugins.atom.patcher.AtomIconSvgPatcher.SvgPatcher
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiUtilCore
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

data class AtomRegexIcon(
    val name: String,
    val iconType: String,
    val priority: Int = 100,
    val pattern: String,
    val icon: String,

    val folderNames: String? = "",
    val iconColor: String? = "808080",
    val folderColor: String? = "808080",
    val folderIconColor: String? = "808080",

    var compiledPattern: Pattern? = null,
) {
    fun match(virtualFile: VirtualFile): Boolean {
        virtualFile.let {
            if (iconType == "FILE" && listOf("Kotlin", "Java").contains(name)) {
                return false
            }
            return try {
                if (compiledPattern == null) compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE)
                var target = virtualFile.name
                if (pattern.contains("/")) {
                    target = virtualFile.path
                }
                compiledPattern!!.matcher(target).matches()
            } catch (e: PatternSyntaxException) {
                thisLogger().info("zylog#", e)
                false
            }
        }
    }

    fun match(element: PsiElement): Boolean {
        val virtualFile = PsiUtilCore.getVirtualFile(element)
        virtualFile?.let {
            return match(virtualFile)
        } ?: return false
    }

    fun matchesName(assocName: String): Boolean = name == assocName

    fun matchColor(key: String): String? {
        return when (key) {
            SvgPatcher.ICON_COLOR -> iconColor
            SvgPatcher.FOLDER_COLOR -> folderColor
            SvgPatcher.FOLDER_ICON_COLOR -> folderIconColor
            else -> "808080"
        };
    }
}

