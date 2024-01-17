package com.example.easy_tiffin.ui//package com.example.TiffinBox.ui
//
//import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
//import org.junit.Assert.*
//
//import org.junit.Test
//
//class Welcome_screenTest {
//
//    @Test
//    fun getButtn_Get_started() {
//        val welcomeScreen = Welcome_screen()
//
//        // Act
//        val result = welcomeScreen.addValues(5, 6)
//
//        // Assert
//        assertEquals(11, result)
//    }
//
//    @Test
//    fun setButtn_Get_started() {
//        val welcomeScreen = Welcome_screen()
//
//        // Act
//        val result = welcomeScreen.addValues(5, 6)
//
//        // Assert
//        assertEquals(11, result)
//    }
//
//    @Test
//    fun onCreate() {
//    }
//
//    @Test
//    fun addValues_shouldReturnTrue() {
//
//        getInstrumentation().runOnMainSync(Runnable {
//           // val context = InstrumentationRegistry.getInstrumentation().targetContext
//
//            // Here you can call methods which have Handler
//            val welcomeScreen = Welcome_screen()
//            val val1 = 5
//            val val2 = 6
//
//            // Act
//            val result = welcomeScreen.addValues(val1, val2)
//
//            // Assert
//            assertTrue(result)
//
//        })
//        // Arrange
//
//    }
//
//    @Test
//    fun addValues_shouldReturnFalse() {
//        // Arrange
//        val welcomeScreen = Welcome_screen()
//        val val1 = 4
//        val val2 = 6
//
//        // Act
//        val result = welcomeScreen.addValues(val1, val2)
//
//        // Assert
//        assertFalse(result)
//    }
//}