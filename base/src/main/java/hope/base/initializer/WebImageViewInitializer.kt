package hope.base.initializer

import android.content.Context
import androidx.startup.Initializer
import hope.base.ui.widget.fresco.FrescoHelper

class WebImageViewInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        FrescoHelper.init(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(ContextInitializer::class.java)
    }

}