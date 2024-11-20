package bunny.init

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "bunny", storages = [Storage("bunny.xml")])
class BunnyConfig : PersistentStateComponent<BunnyConfig.Stage> {
    private var stage = Stage()

    data class Stage(var initial: Boolean = false)

    override fun getState(): Stage {
        return stage
    }

    override fun loadState(state: Stage) {
        this.stage.initial = state.initial
    }
}