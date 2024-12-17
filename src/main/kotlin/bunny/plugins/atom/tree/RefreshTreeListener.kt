package bunny.plugins.atom.tree

import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/** Refresh tree when file is open, for hollow directories. */
class RefreshTreeListener : FileEditorManagerListener {

    /** Refresh tree when files are open, for hollow directories. */
    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        refresh(source.project)
    }

    /** Refresh tree when files are open, for hollow directories. */
    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        refresh(source.project)
    }

    private fun refresh(project: Project?) {
        if (project != null) {
            val view = ProjectView.getInstance(project)
            if (view != null) {
                view.refresh()
                if (view.currentProjectViewPane != null) view.currentProjectViewPane.updateFromRoot(true)
            }
        }
    }

}
