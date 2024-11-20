package icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/*
 * https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html
 */
object Icons {
    @JvmField
    val terminal_icon = IconLoader.getIcon("/icons/terminal.png", javaClass)

    @JvmField
    val mybatis_icon = IconLoader.getIcon("/icons/mybatis.svg", javaClass)
}
