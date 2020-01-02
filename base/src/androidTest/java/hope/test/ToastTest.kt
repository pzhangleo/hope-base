package hope.test

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import hope.base.AppConstant
import hope.base.extensions.longToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToastTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Test
    fun toastTest() = runBlocking(Dispatchers.IO){
        val context = ApplicationProvider.getApplicationContext<Application>()
        AppConstant.init(context)
        "This is a test toast!".longToast()
    }

}