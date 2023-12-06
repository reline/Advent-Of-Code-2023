import kotlin.test.Test
import kotlin.test.assertEquals

class DaySixTest {
    @Test
    fun testPartOne() {
        val input = """
            Time:      7  15   30
            Distance:  9  40  200
        """.trimIndent()
        assertEquals(288, WaitForIt.partone(input))
    }

    @Test
    fun testPartTwo() {
        val input = """
            Time:      7  15   30
            Distance:  9  40  200
        """.trimIndent()
        assertEquals(71503, WaitForIt.parttwo(input))
    }
}