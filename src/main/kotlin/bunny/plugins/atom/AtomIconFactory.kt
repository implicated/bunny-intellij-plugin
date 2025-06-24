package bunny.plugins.atom

import bunny.plugins.atom.patcher.AtomIconPatcher
import icons.Icons
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

object AtomIconFactory {

    fun regexParse(path: String): List<AtomRegexIcon> {

        // 创建解析器
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()

        // 解析 XML 文件
        val document = builder.parse(Icons.javaClass.getResourceAsStream(path))

        // 获取根元素
        val root = document.documentElement

        // 获取所有 <regex> 节点
        val regexNodes = root.getElementsByTagName("regex")

        val regexList = mutableListOf<AtomRegexIcon>()
        for (i in 0 until regexNodes.length) {
            val regexNode = regexNodes.item(i)
            if (regexNode.nodeType == Node.ELEMENT_NODE) {
                val element = regexNode as Element

                // 获取 <regex> 的属性
                val name = element.getAttribute("name")
                val iconType = element.getAttribute("iconType")
                val priority = element.getAttribute("priority").toInt()
                val pattern = element.getAttribute("pattern")
                val icon = element.getAttribute("icon")
                val folderNames = element.getAttribute("fileNames")
                val folderColor = element.getAttribute("folderColor") // 未定义时返回 null
                val folderIconColor = element.getAttribute("folderIconColor") // 未定义时返回 null

                // 创建 Regex 对象并加入列表
                regexList.add(
                    AtomRegexIcon(
                        name = name,
                        iconType = iconType,
                        priority = priority,
                        pattern = pattern,
                        icon = icon,
                        folderNames = folderNames,
                        folderColor = folderColor,
                        folderIconColor = folderIconColor,
                    )
                )
            }
        }
        return regexList.toList()
    }

    fun patcherParse(path: String): Set<AtomIconPatcher> {
        val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = builder.parse(Icons.javaClass.getResourceAsStream(path))

        val root = document.documentElement

        val patcherList = mutableSetOf<AtomIconPatcher>()
        /**
         * iconPatcher ->
         * glyphPatcher -> PSI
         * filePatcher ->
         */
        for (type in listOf("iconPatcher", "glyphPatcher", "filePatcher")) {
            val regexNodes = root.getElementsByTagName(type)

            for (i in 0 until regexNodes.length) {
                val regexNode = regexNodes.item(i)
                if (regexNode.nodeType == Node.ELEMENT_NODE) {
                    val element = regexNode as Element

                    val name = element.getAttribute("name")
                    val remove = element.getAttribute("remove")
                    val append = element.getAttribute("append")

                    // 创建 Regex 对象并加入列表
                    patcherList.add(
                        AtomIconPatcher(
                            type = type,
                            name = name,
                            remove = remove,
                            append = append,
                        )
                    )
                }
            }
        }
        return patcherList
    }

}