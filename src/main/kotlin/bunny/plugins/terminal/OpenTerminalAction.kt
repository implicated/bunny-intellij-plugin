package bunny.plugins.terminal

import com.intellij.ide.actions.RevealFileAction
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.vfs.VirtualFile
import java.io.File
import java.io.IOException
import java.util.stream.Collectors
import java.util.stream.Stream

class OpenTerminalAction : DumbAwareAction() {
    override fun actionPerformed(event: AnActionEvent) {
        try {
            val directory = getDirectory(event)
            ProcessBuilder(Stream.of("open", directory, "-a", "kitty").collect(Collectors.toList()))
                .directory(File(directory))
                .start()
        } catch (e: IOException) {
            throw RuntimeException("Failed to execute the command!", e)
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(event: AnActionEvent) {
        val project = getEventProject(event)
        event.presentation.isEnabledAndVisible =
            project != null && getSelectedFile(event) != null
    }

    private fun getDirectory(event: AnActionEvent): String {
        val directory = getSelectedDirectory(event) ?: return System.getProperty("user.home")
        return directory.path
    }


    private fun getSelectedDirectory(event: AnActionEvent): VirtualFile? {
        val file = getSelectedFile(event)
        return if (file != null
        ) if (file.isDirectory) file else file.parent
        else null
    }

    companion object {
        private fun getSelectedFile(event: AnActionEvent): VirtualFile? {
            return RevealFileAction.findLocalFile(event.getData(CommonDataKeys.VIRTUAL_FILE))
        }
    }
}
