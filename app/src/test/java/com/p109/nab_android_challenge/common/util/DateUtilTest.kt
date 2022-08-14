package com.p109.nab_android_challenge.common.util

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateUtilTest {

    @Test
    fun `convert time in millis to date string`() {
        val timeInMillis = 1660541158801L
        val actualDate = DateUtil.convertFromTimeStamp(timeInMillis)
        val expectDate = "Mon, 15 Aug 2022"
        Assert.assertEquals(expectDate, actualDate)
    }
}