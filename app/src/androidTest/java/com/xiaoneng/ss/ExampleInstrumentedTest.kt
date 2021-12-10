package com.xiaoneng.ss

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.xiaoneng.ss", appContext.packageName)
    }

    @Test
    fun twoSum(nums: IntArray, target: Int): IntArray {
        var result = intArrayOf()
        for (i in 0..nums.size) {
            for (j in 0..nums.size) {
                if (j != i) {
                    if (nums[i] + nums[j] == target) {
                        result[0] = i
                        result[1] = j
                        return result
                    }
                }
            }
        }
        return result
    }
}