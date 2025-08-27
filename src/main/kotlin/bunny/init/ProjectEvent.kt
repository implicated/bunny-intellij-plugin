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
            // @formatter:off
            when (plugin.pluginId.idString) {

                // bundled
                KotlinCodeStyleService.PLUGIN_ID -> project.service<KotlinCodeStyleService>().commentStyle(project)
                JavaCodeStyleService.PLUGIN_ID -> project.service<JavaCodeStyleService>().commentStyle(project)
                PropertiesCodeStyleService.PLUGIN_ID -> project.service<PropertiesCodeStyleService>().customStyle(project)

                // marketplace
            }
            // @formatter:on
        }

        project.service<VcsService>().setCommitChecks(project)
    }
}