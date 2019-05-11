package com.github.jeremyrempel.unsplash

import io.ktor.util.date.GMTDate
import kotlin.test.*

class DateTest {
    @Test
    fun testParse() {
        val date = GMTDate(10000)
        assertNotNull(date)
    }
}
