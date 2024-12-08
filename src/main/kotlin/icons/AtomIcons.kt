package icons

import bunny.plugins.atom.RegexIcon
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.vfs.VFileProperty
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.LayeredIcon
import javax.swing.Icon
import javax.swing.SwingConstants

/*
 * https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html
 */
object AtomIcons {
    @JvmField
    val files = listOf(
        RegexIcon(
            iconType = "/files",
            name = "Configs",
            icon = "/icons/files/config.svg",
            pattern = ".*/(config|settings?|options?|prefs?)(.(ya?ml|ini|txt|json))?$"
        )
    )

    @JvmField
    val folders = listOf(
        RegexIcon(
            iconType = "/folders",
            name = "Configs",
            icon = "/icons/folders/config.svg",
            pattern = "^[._]?(cfg|configs?|conf(iguration)?|settings?|options?)$"
        )
    )

    @JvmField
    val foldersOpen = listOf(
        RegexIcon(
            iconType = "/folders",
            name = "Configs",
            icon = "/icons/folders/config.svg",
            pattern = "^[._]?(cfg|configs?|conf(iguration)?|settings?|options?)$"
        )
    )

    fun getIcon(path: String): Icon =
        IconLoader.getIcon(path, Icons.javaClass)

    fun getLayeredIcon(icon: Icon, virtualFile: VirtualFile): Icon =
        when {
            virtualFile.`is`(VFileProperty.SYMLINK) -> this.create(icon, AllIcons.Nodes.Symlink)
            !virtualFile.isWritable -> this.create(icon, AllIcons.Nodes.Locked)
            else -> icon
        }

    private fun create(mainIcon: Icon, vararg layeredIcons: Icon): Icon {
        val layeredIcon = LayeredIcon(layeredIcons.size + 1)
        layeredIcon.setIcon(mainIcon, 0)

        layeredIcons.forEachIndexed { index, icon ->
            layeredIcon.setIcon(icon, index + 1, SwingConstants.SOUTH_EAST)
        }
        return layeredIcon
    }
}
