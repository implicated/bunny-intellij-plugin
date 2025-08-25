package bunny.init

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

@Service
@State(name = "bunny", storages = [Storage(value = "bunny.xml", roamingType = RoamingType.DISABLED)])
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