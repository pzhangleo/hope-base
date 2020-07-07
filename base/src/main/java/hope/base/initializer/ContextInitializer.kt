package hope.base.initializer

import android.content.Context
import androidx.startup.Initializer
import hope.base.AppConstant

class ContextInitializer: Initializer<Unit> {
    override fun create(context: Context): Unit {
        AppConstant.init(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}