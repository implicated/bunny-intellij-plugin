package bunny.plugins.atom.tree

import cache.AppCache
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.ide.projectView.impl.ProjectRootsUtil
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.isFile
import com.intellij.util.PlatformIcons
import icons.Icons
import javax.swing.Icon

class HollowFoldersDecorator : ProjectViewNodeDecorator, DumbAware {

    private val excluded: Icon = Icons.getIcon("/icons/atom/patch/icons/mt/modules/ExcludedTreeOpen.svg")
    private val module: Icon = Icons.getIcon("/icons/atom/patch/icons/mt/modules/ModuleOpen.svg")
    private val source: Icon = Icons.getIcon("/icons/atom/patch/icons/mt/modules/sourceRootOpen.svg")
    private val test: Icon = Icons.getIcon("/icons/atom/patch/icons/mt/modules/testRootOpen.svg")
    private val folderOpen: Icon = Icons.getIcon("/icons/atom/patch/icons/nodes/folderOpen.svg")

    /**
     * @see com.intellij.ide.projectView.impl.CompoundProjectViewNodeDecorator.decorate
     */
    override fun decorate(node: ProjectViewNode<*>, data: PresentationData) {
        val project = node.project ?: return
        if (project.isDisposed) return
        val virtualFile = node.virtualFile ?: return

        val icon = when {
            virtualFile.isFile -> matchFiles(virtualFile)
            else -> matchFolder(virtualFile, data, project)
        } ?: return
        data.setIcon(Icons.getLayeredIcon(icon, virtualFile))
    }

    private fun matchFiles(virtualFile: VirtualFile): Icon? {
        return Icons.files
            .filter { it.match(virtualFile) }
            .maxByOrNull { it.priority }
            ?.let {
                AppCache.instance.iconCache.getOrPut(it.icon) {
                    Icons.getIcon(it.icon)
                }
            }
    }

    private fun matchFolder(virtualFile: VirtualFile, data: PresentationData, project: Project): Icon? {
        val openFiles = FileEditorManager.getInstance(project).openFiles
        val isOpen = openFiles.any { vf -> vf.path.contains(virtualFile.path) }
        return if (!isOpen) {
            Icons.folders
                .filter { it.match(virtualFile) }
                .maxByOrNull { it.priority }
                ?.let {
                    AppCache.instance.iconCache.getOrPut(it.icon) {
                        Icons.getIcon(it.icon)
                    }
                }
        } else {
            when {
                data.getIcon(true) == PlatformIcons.PACKAGE_ICON -> PlatformIcons.PACKAGE_ICON
                ProjectRootManager.getInstance(project).fileIndex.isExcluded(virtualFile) -> excluded
                ProjectRootsUtil.isModuleContentRoot(virtualFile, project) -> module
                ProjectRootsUtil.isInSource(virtualFile, project) -> source
                ProjectRootsUtil.isInTestSource(virtualFile, project) -> test
                else -> {
                    Icons.foldersOpen
                        .filter { it.match(virtualFile) }
                        .maxByOrNull { it.priority }
                        ?.let {
                            AppCache.instance.iconCache.getOrPut(it.icon) {
                                Icons.getIcon(it.icon)
                            }
                        }
                        ?: folderOpen
                }
            }
        }
    }
}
