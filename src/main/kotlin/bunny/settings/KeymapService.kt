package bunny.settings

import com.intellij.openapi.components.Service
import com.intellij.openapi.keymap.KeymapManager
import com.intellij.openapi.keymap.impl.KeymapManagerImpl

@Service
class KeymapService {
    private val defaultKeymap: String = "vim"

    fun setActiveKeymap() {
        val keymapManager = KeymapManager.getInstance() as KeymapManagerImpl
        keymapManager.allKeymaps.find { it.name == defaultKeymap }
            ?.let { keymap ->
                keymapManager.activeKeymap = keymap
            }
    }
}
