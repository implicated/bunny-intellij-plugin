package bunny.init

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@Service
@State(name = "bunny", storages = [Storage("bunny.xml")])
class BunnyConfig : PersistentStateComponent<BunnyConfig.BunnyState> {
    private var state = BunnyState()

    override fun getState(): BunnyState = state

    override fun loadState(state: BunnyState) {
        XmlSerializerUtil.copyBean(state, this.state)
    }

    class BunnyState(
        var firstRun: Boolean = true
    )
}