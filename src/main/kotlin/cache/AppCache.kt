package cache

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import javax.swing.Icon

@Service(Service.Level.APP)
class AppCache {
    internal val iconCache = mutableMapOf<String, Icon?>()
    internal val iconPathCache = mutableMapOf<String, String?>()

    companion object {
        /** Get the instance of the service. */
        val instance: AppCache by lazy { service() }
    }
}
