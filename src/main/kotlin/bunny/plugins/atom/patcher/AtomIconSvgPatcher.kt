@file:Suppress("unused")

package bunny.plugins.atom.patcher

import bunny.plugins.atom.AtomRegexIcon
import com.dynatrace.hash4j.hashing.HashFunnel
import com.dynatrace.hash4j.hashing.Hashing
import com.intellij.openapi.components.Service
import com.intellij.ui.ColorUtil
import com.intellij.ui.JBColor
import com.intellij.ui.svg.SvgAttributePatcher
import com.intellij.util.SVGLoader.SvgElementColorPatcherProvider
import com.intellij.util.ui.UIUtil
import icons.Icons
import javax.swing.plaf.ColorUIResource

@Suppress("UnstableApiUsage")
@Service(Service.Level.APP)
class AtomIconSvgPatcher : SvgElementColorPatcherProvider {
    private var accentColor: String = ColorUtil.toHex(ColorUIResource(ColorUtil.fromHex(getAccentFromTheme())))
    private val themedColor: String = ColorUtil.toHex(ColorUIResource(ColorUtil.fromHex(getThemedFromTheme())))

    /**
     * @see com.intellij.ui.svg.readAttributes
     * @see com.intellij.ui.svg.renderImage
     */
    override fun attributeForPath(path: String): SvgAttributePatcher = object : SvgAttributePatcher {
        override fun patchColors(attributes: MutableMap<String, String>) {
            // 应用 XML 的中 color
            // patchIconColor(attributes)
            // patchFolderColor(attributes)
            // patchFolderIconColor(attributes)

            // 应用 theme 的中 color
            patchAccentColor(attributes)
            patchThemeColor(attributes)
            // patchBigIconColor(attributes)
        }
    }

    /**
     * @see com.intellij.ui.svg.colorPatcherDigestShim
     * @see com.intellij.ui.svg.SvgCacheManager
     */
    override fun digest(): LongArray {
        val hasher = mutableListOf<Long>()

        // val fileAssociations = Icons.files
        // val folderAssociations = Icons.folders
        // fileAssociations.forEach {
        //     hasher.add((it.iconColor ?: "").toHash())
        // }
        // folderAssociations.forEach {
        //     hasher.add((it.folderColor ?: "").toHash())
        //     hasher.add((it.folderIconColor ?: "").toHash())
        // }

        hasher.add(accentColor.toHash())
        hasher.add(themedColor.toHash())

        return hasher.toLongArray()
    }


    private fun patchIconColor(attributes: MutableMap<String, String>) {
        patchSvg(Icons.files, ICON_COLOR, attributes)
    }

    private fun patchFolderColor(attributes: MutableMap<String, String>) {
        patchSvg(Icons.folders, FOLDER_COLOR, attributes)
    }

    private fun patchFolderIconColor(attributes: MutableMap<String, String>) {
        patchSvg(Icons.folders, FOLDER_ICON_COLOR, attributes)
    }

    private fun patchAccentColor(attributes: MutableMap<String, String>) {
        val tint = attributes[TINT] ?: return
        patchColor(tint, accentColor, attributes)
    }

    private fun patchThemeColor(attributes: MutableMap<String, String>) {
        val themed = attributes[THEMED] ?: return
        patchColor(themed, themedColor, attributes)
    }

    private fun patchBigIconColor(attributes: MutableMap<String, String>) {
        val hasWidth = attributes[WIDTH]
        if (hasWidth == "16" || hasWidth == "16px") {
            attributes[WIDTH] = "16$PX"
            attributes[HEIGHT] = "16$PX"
        }
    }


    private fun patchSvg(icons: List<AtomRegexIcon>, key: String, attributes: MutableMap<String, String>) {
        val attr = attributes[key] ?: return
        icons.filter { it.matchesName(attr) }
            .maxByOrNull { it.priority }
            ?.let {
                val color = it.matchColor(key)
                attributes[FILL] = "#$color"
                if (attributes[STROKE] != null && attributes[STROKE] != "") {
                    attributes[STROKE] = "#$color"
                }
            }
    }

    private fun patchColor(tag: String, color: String, attributes: MutableMap<String, String>) {
        // if tint = "true" or tint = "fill", change the fill color. If tint = "stroke", change the stroke color
        when (tag) {
            TRUE, FILL -> attributes[FILL] = "#$color"
            STROKE -> attributes[STROKE] = "#$color"
        }
    }

    /** Extract accent color from current theme. */
    private fun getAccentFromTheme(): String =
        ColorUtil.toHex(
            JBColor.namedColor(
                "Link.activeForeground",
                UIUtil.getButtonSelectColor() ?: UIUtil.getListSelectionForeground(true)
            )
        )

    /** Extract themed color from current theme. */
    private fun getThemedFromTheme(): String =
        ColorUtil.toHex(
            JBColor.namedColor(
                "Tree.foreground",
                UIUtil.getLabelForeground()
            )
        )

    private fun String.toHash(): Long =
        Hashing.komihash4_3().hashStream().putOrderedIterable(listOf(this), HashFunnel.forString()).asLong


    companion object SvgPatcher {
        // val instance: AtomIconSvgPatcher by lazy { service() }
        const val ICON_COLOR: String = "data-iconColor"
        const val FOLDER_COLOR: String = "data-folderColor"
        const val FOLDER_ICON_COLOR: String = "data-folderIconColor"
        const val TINT: String = "data-tint"
        const val THEMED: String = "data-themed"
        const val BIG: String = "data-big"

        const val TRUE: String = "true"
        const val FILL: String = "fill"
        const val STROKE: String = "stroke"
        const val WIDTH: String = "width"
        const val HEIGHT: String = "height"
        const val PX: String = "px"
    }
}