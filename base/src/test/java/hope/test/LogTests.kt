package hope.test

import hope.base.log.ZLog
import org.junit.Test

class LogTests {

    @Test
    fun testTimber() {
        ZLog.init(true)
    }
}