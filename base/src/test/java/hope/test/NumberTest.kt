package hope.test

import hope.base.extensions.stripTrailingZeros
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NumberTest {

    @Test
    fun testZero() {
        val number = "1.023000000"
        Assert.assertEquals("1.023", number.stripTrailingZeros())
    }
}
