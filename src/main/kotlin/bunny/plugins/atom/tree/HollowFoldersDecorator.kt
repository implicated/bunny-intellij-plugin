package bunny.plugins.atom.tree

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.ide.projectView.impl.ProjectRootsUtil
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.PlatformIcons
import icons.AtomIcons
import java.util.*
import javax.swing.Icon

/**
 * Hollow folders' decorator: Decorate directories as "open" when one of
 * its files is open.
 */
class HollowFoldersDecorator : ProjectViewNodeDecorator {
    /** Decorate nodes with icon associations. */
    override fun decorate(node: ProjectViewNode<*>, data: PresentationData) {
        val file = node.virtualFile
        val project = node.project

        if (project != null && file != null && !project.isDisposed) {
            when {
                !file.isDirectory -> return
                isFolderContainingOpenFiles(project, file) -> setOpenDirectoryIcon(data, file, project)
            }
        }
    }

    private fun isFolderContainingOpenFiles(project: Project, virtualFile: VirtualFile): Boolean {
        val openFiles = FileEditorManager.getInstance(project).openFiles
        return openFiles.any { vf: VirtualFile -> vf.path.contains(virtualFile.path) }
    }

    /**
     * Set open directory icon according to the directory type
     *
     * @param data Presentation Data
     * @param file data about the directory
     * @param project current project
     */
    private fun setOpenDirectoryIcon(data: PresentationData, file: VirtualFile, project: Project) {
        // try {
        //     val matchedAssociation = matchAssociation(file)
        //     val icon = when {
        //         data.getIcon(/* open = */ true) is DirIcon -> {
        //             val openedIcon: Icon = (Objects.requireNonNull(data.getIcon(true)) as DirIcon).openedIcon
        //             DirIcon(openedIcon)
        //         }
        //
        //         matchedAssociation != null -> matchedAssociation
        //
        //         ProjectRootManager.getInstance(project).fileIndex.isExcluded(file) -> AtomIcons.EXCLUDED
        //         ProjectRootsUtil.isModuleContentRoot(file, project) -> AtomIcons.MODULE
        //         ProjectRootsUtil.isInSource(file, project) -> AtomIcons.SOURCE
        //         ProjectRootsUtil.isInTestSource(file, project) -> AtomIcons.TEST
        //         data.getIcon(/* open = */ false) == PlatformIcons.PACKAGE_ICON -> PlatformIcons.PACKAGE_ICON
        //         else -> directoryIcon
        //     }
        //
        //     val layeredIcon = AtomIcons.getLayeredIcon(icon, file)
        //     data.setIcon(layeredIcon)
        // } catch (e: Exception) {
        //     thisLogger().warn(e.message)
        // }
    }

    private fun matchAssociation(virtualFile: VirtualFile): Icon? {
        val regex = AtomIcons.foldersOpen
            .filter { it.match(virtualFile) }
            .maxByOrNull { it.priority }
        return regex?.let {
            AtomIcons.getLayeredIcon(AtomIcons.getIcon(it.icon), virtualFile)
        }
    }

    // companion object {
    //     /** Default directory icon. */
    //     val directoryIcon: Icon
    //         get() = AtomIcons.Nodes2.FolderOpen
    // }

}
