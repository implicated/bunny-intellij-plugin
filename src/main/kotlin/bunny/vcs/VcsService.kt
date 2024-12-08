package bunny.vcs

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.VcsConfiguration

@Service(Service.Level.PROJECT)
class VcsService {
    fun setCommitChecks(project: Project) {
        val vcsConfiguration = VcsConfiguration.getInstance(project)
        vcsConfiguration.CHECK_NEW_TODO = false
        vcsConfiguration.CHECK_CODE_SMELLS_BEFORE_PROJECT_COMMIT = false
    }
}