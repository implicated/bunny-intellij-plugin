package bunny.init

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.Property

@Service
@State(name = "bunny", storages = [Storage("bunny.xml")])
class BunnyConfig : PersistentStateComponent<BunnyConfig> {
    @Property
    var firstRun: Boolean = true

    override fun getState(): BunnyConfig = this

    override fun loadState(state: BunnyConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }
}