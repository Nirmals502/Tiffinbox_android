package com.example.easy_tiffin

import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Test

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.retrofi_implementation_", appContext.packageName)
    }
}
   // @Test
//    fun setButtn_Get_started() {
//        // Arrange
//        val welcomeScreen = Welcome_screen()
//
//        // Act
//        val result = welcomeScreen.addValues(5, 6)
//
//        // Assert
//        assertEquals(11, result)
//    }
