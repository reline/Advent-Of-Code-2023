import kotlin.test.Test
import kotlin.test.assertEquals

class DayThreeTest {
    @Test
    fun testPartOne() {
        val input = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
        """.trimIndent()
        assertEquals(4361, GearRatios.partone(input))
    }

    @Test
    fun testPartTwo() {
        val input = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
        """.trimIndent()
        assertEquals(467835, GearRatios.parttwo(input))
    }
}