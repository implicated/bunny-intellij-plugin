package bunny.plugins.atom

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiUtilCore
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException
import javax.swing.Icon

data class RegexIcon(
    val iconType: String,
    val name: String,
    val icon: String,
    val pattern: String,
    val priority: Int = 100,
    val folderNames: String? = "",
    val iconColor: String? = "808080",
    val folderColor: String? = "808080",
    val folderIconColor: String? = "808080",

    var compiledPattern: Pattern? = null,
    var iconObj: Icon? = null
) {
    fun match(virtualFile: VirtualFile): Boolean {
        virtualFile.let {
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
}

