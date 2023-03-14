package no.ntnu.vsbugge.calculator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Test the calculator
 */
class TestCalculate {
    @Test
    fun testAddition() {
        val operation = Operation(1.0, 2.0, '+')
        val result = ArithmeticService.calculate(operation)
        assertEquals(3.0, result.result)
    }

    @Test
    fun testSubtraction() {
        val operation = Operation(1.0, 2.0, '-')
        val result = ArithmeticService.calculate(operation)
        assertEquals(-1.0, result.result)
    }

    @Test
    fun testMultiplication() {
        val operation = Operation(1.0, 2.0, '*')
        val result = ArithmeticService.calculate(operation)
        assertEquals(2.0, result.result)
    }

    @Test
    fun testDivision() {
        val operation = Operation(1.0, 2.0, '/')
        val result = ArithmeticService.calculate(operation)
        assertEquals(0.5, result.result)
    }
}