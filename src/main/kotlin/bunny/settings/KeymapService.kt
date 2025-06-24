package bunny.settings

import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.components.Service
import com.intellij.openapi.keymap.KeymapManager
import com.intellij.openapi.keymap.impl.KeymapManagerImpl

@Service
class KeymapService {
    private val defaultKeymap: String = "vim"
    private val dataGripKeymap: String = "datagrip"
    private val asciiDocKeymap: String = "asciidoc"

    fun setActiveKeymap() {
        val keymapManager = KeymapManager.getInstance() as KeymapManagerImpl

        val productCode = ApplicationInfo.getInstance().build.productCode
        when (productCode) {
            "DB" -> keymapManager.allKeymaps.find { it.name == defaultKeymap }
            "AC" -> keymapManager.allKeymaps.find { it.name == asciiDocKeymap }
            else -> keymapManager.allKeymaps.find { it.name == defaultKeymap }
        }?.let { keymap ->
            keymapManager.activeKeymap = keymap
        }
    }
}
