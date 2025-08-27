package bunny.init

import bunny.styles.*
import bunny.vcs.VcsService
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class ProjectEvent : ProjectActivity {

    override suspend fun execute(project: Project) {

        PluginManagerCore.loadedPlugins.forEach { plugin ->
            when (plugin.pluginId.idString) {
                SqlCodeStyleService.PLUGIN_ID -> project.service<SqlCodeStyleService>().customStyle(project)
            }
        }

        project.service<VcsService>().setCommitChecks(project)
    }
}