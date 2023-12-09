import kotlin.test.Test
import kotlin.test.assertEquals

class DayEightTest {
    @Test
    fun testPartOne() {
        val input = """
            RL

            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent()
        assertEquals(2, HauntedWasteland.partone(input))
    }

    @Test
    fun testPartOneRepeating() {
        val input = """
            LLR

            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent()
        assertEquals(6, HauntedWasteland.partone(input))
    }

    @Test
    fun testPartTwo() {
        val input = """
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent()
        assertEquals(6, HauntedWasteland.parttwo(input))
    }
}