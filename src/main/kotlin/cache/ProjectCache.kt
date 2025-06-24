package cache

import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.openapi.components.Service
import java.util.*
import javax.swing.Icon

@Service(Service.Level.PROJECT)
class ProjectCache {
    internal val treeCache = WeakHashMap<ProjectViewNode<*>, Icon?>()
}
