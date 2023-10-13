package com.example.tmbdmovieapp

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun testReverseString() {
        val input = "Hello, world!"
        val expectedOutput = "!dlrow ,olleH"
        val actualOutput = reverseString(input)
        assertEquals(expectedOutput, actualOutput)
    }

    private fun reverseString(input: String): String {
        return input.reversed()
    }
}