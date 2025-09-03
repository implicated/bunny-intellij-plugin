package bunny.plugins.atom.provider

import cache.AppCache
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.FilePath
import com.intellij.openapi.vcs.changes.FilePathIconProvider
import icons.Icons
import javax.swing.Icon

class AtomIconVcsProvider : FilePathIconProvider {

    /**
     *
     */
    override fun getIcon(filePath: FilePath, project: Project?): Icon? {
        val regex = filePath.virtualFile?.let { virtualFile ->
            Icons.files
                .filter { it.match(virtualFile) }
                .maxByOrNull { it.priority }
        }
        return regex?.let {
            AppCache.instance.iconCache.getOrPut(it.icon) {
                Icons.getIcon(it.icon)
            }
        }
    }
}