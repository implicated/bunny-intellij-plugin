package bunny.init

import bunny.vcs.VcsService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class ProjectEvent : ProjectActivity {

    override suspend fun execute(project: Project) {

        // vcs commit
        project.service<VcsService>().setCommitChecks(project)
    }
}