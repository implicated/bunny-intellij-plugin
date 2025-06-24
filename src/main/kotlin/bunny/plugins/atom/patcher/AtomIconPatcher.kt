package bunny.plugins.atom.patcher

import cache.AppCache
import com.intellij.openapi.util.IconPathPatcher
import icons.Icons

class AtomIconPatcher(
    val type: String,
    val name: String,
    val remove: String,
    val append: String
) : IconPathPatcher() {


    /**
     * @see com.intellij.ui.icons.IconTransform.applyPatchers
     */
    override fun patchPath(path: String, classLoader: ClassLoader?): String? {
        if (!path.contains(this.remove)) {
            return null
        }
        return AppCache.instance.iconPathCache.getOrPut(path) {
            tryLoadResource(path, "svg") ?: tryLoadResource(path, "png")
        }
    }

    override fun getContextClassLoader(path: String, originalClassLoader: ClassLoader?): ClassLoader? {
        return Icons.javaClass.classLoader
    }

    // 尝试加载资源的通用逻辑
    private fun tryLoadResource(path: String, extension: String): String? {
        val modifiedPath = path.replaceAfterLast(".", extension).replace(remove, "")
        val resourcePath = (append + modifiedPath).replace("//", "/")
        return if (Icons.javaClass.getResource(resourcePath) != null) resourcePath else null
    }
}