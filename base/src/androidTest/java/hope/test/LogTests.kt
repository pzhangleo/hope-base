package hope.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import hope.base.extensions.logv
import hope.base.log.ZLog
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogTests {

    @Test
    fun testTimber() {
        ZLog.init(true)
        ZLog.v("Test test test test!!!")
        "Extensions test test test".logv()
    }
}
