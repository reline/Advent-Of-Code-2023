import kotlin.test.Test
import kotlin.test.assertEquals

class DayNineTest {
    @Test
    fun testPartOne() {
        val input = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
        """.trimIndent()
        assertEquals(114, MirageMaintenance.partone(input))
    }

    @Test
    fun testNextValueZeros() {
        assertEquals(0, listOf(0, 0, 0, 0).nextValue())
    }

    @Test
    fun testNextValueThrees() {
        assertEquals(3, listOf(3, 3, 3, 3, 3).nextValue())
    }

    @Test
    fun testNextValue_StepBy3() {
        assertEquals(18, listOf(0, 3, 6, 9, 12, 15).nextValue())
    }

    @Test
    fun testPartTwo() {
        val input = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
        """.trimIndent()
        assertEquals(2, MirageMaintenance.parttwo(input))
    }
}