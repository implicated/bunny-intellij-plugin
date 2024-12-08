package bunny.plugins.atom.tree

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.vfs.VirtualFile
import icons.AtomIcons
import icons.Icons

/** New custom files decorator. */
class DefaultFilesDecorator : ProjectViewNodeDecorator {

    /** Update node icon. */
    override fun decorate(node: ProjectViewNode<*>, data: PresentationData) {
        val file = node.virtualFile
        val project = node.project

        if (project != null && !project.isDisposed) {
            when {
                file == null -> return
                file.isDirectory -> return
                else -> matchAssociation(file, data)
            }

        }
    }

    private fun matchAssociation(virtualFile: VirtualFile, data: PresentationData) {
        val regex = AtomIcons.files
            .filter { it.match(virtualFile) }
            .maxByOrNull { it.priority }
        data.setIcon(
            regex?.let {
                IconLoader.getIcon(it.icon, Icons.javaClass)
            }
        )
    }

}
