package icons

import bunny.plugins.atom.AtomIconFactory
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.io.FileUtil.toCanonicalPath
import com.intellij.openapi.vfs.VFileProperty
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.LayeredIcon
import org.jetbrains.annotations.NonNls
import javax.swing.Icon
import javax.swing.SwingConstants

/**
 * [intellij docs](https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html)
 *
 * [iconGenerator](https://github.com/AtomMaterialUI/iconGenerator)
 *
 * [a-file-icon-idea](https://github.com/AtomMaterialUI/a-file-icon-idea)
 */
object Icons {
    @JvmField
    val terminal_icon = getIcon("/icons/terminal.svg")

    @JvmField
    val mybatis_icon = getIcon("/icons/mybatis.svg")

    @JvmField
    val files = AtomIconFactory.regexParse("/icons/atom/icon_associations.xml")

    @JvmField
    val folders = AtomIconFactory.regexParse("/icons/atom/folder_associations.xml")

    @JvmField
    val foldersOpen = AtomIconFactory.regexParse("/icons/atom/folder_open_associations.xml")

    @JvmField
    val patchers = AtomIconFactory.patcherParse("/icons/atom/icon_patchers.xml")


    fun getIcon(@NonNls path: String): Icon = IconLoader.getIcon(toCanonicalPath(path), Icons.javaClass)

    fun getLayeredIcon(icon: Icon, virtualFile: VirtualFile): Icon =
        when {
            virtualFile.`is`(VFileProperty.SYMLINK) -> this.createLayered(icon, AllIcons.Nodes.Symlink)
            !virtualFile.isWritable -> this.createLayered(icon, AllIcons.Nodes.Locked)
            else -> icon
        }

    private fun createLayered(mainIcon: Icon, vararg layeredIcons: Icon): Icon {
        val layeredIcon = LayeredIcon(layeredIcons.size + 1)
        layeredIcon.setIcon(mainIcon, 0)

        layeredIcons.forEachIndexed { index, icon ->
            layeredIcon.setIcon(icon, index + 1, SwingConstants.SOUTH_EAST)
        }
        return layeredIcon
    }
}
