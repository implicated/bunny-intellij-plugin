package bunny.plugins.mybatis

import com.intellij.openapi.diagnostic.thisLogger
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

object ClipboardUtils {

    var content: String?
        get() {
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            // 获取剪贴板中的内容
            val trans = clipboard.getContents(null)

            if (trans != null) {
                // 判断剪贴板中的内容是否支持文本
                if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        // 获取剪贴板中的文本内容
                        return trans.getTransferData(DataFlavor.stringFlavor) as String
                    } catch (e: Exception) {
                        thisLogger().info("zylog#ClipboardUtils ==> :{${e.message}}", e)
                    }
                }
            }

            return null
        }
        set(text) {
            val selection = StringSelection(text)
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            clipboard.setContents(selection, null)
        }
}
