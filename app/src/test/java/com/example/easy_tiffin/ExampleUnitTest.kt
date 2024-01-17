package com.example.easy_tiffin

import android.content.Context
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
    @Mock
    private lateinit var mockContext: Context

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

//    @Test
//    fun addValues() {
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
//    fun addValues_shouldReturnTrue() {
//        // Arrange
//        val welcomeScreen = Welcome_screen()
//        val val1 = 5
//        val val2 = 6
//
//        // Act
//        val result = welcomeScreen.addValues(val1, val2)
//
//        // Assert
//        assertTrue(result)
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
