import kotlin.test.Test
import kotlin.test.assertEquals

class DayOneTest {
    @Test
    fun testPartOne() {
        val input = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
        """.trimIndent()
        assertEquals(142, Trebuchet.partone(input))
    }

    @Test
    fun testPartTwo() {
        val input = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent()
        assertEquals(281, Trebuchet.parttwo(input))
    }
}