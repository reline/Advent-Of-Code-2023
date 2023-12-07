import kotlin.test.Test
import kotlin.test.assertEquals
import Strength.*

class DaySevenTest {
    @Test
    fun testPartOne() {
        val input = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent()
        assertEquals(6440, CamelCards.partone(input))
    }

    @Test
    fun testPartTwo() {
        val input = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent()
        assertEquals(5905, CamelCards.parttwo(input))
    }

    @Test
    fun testHandStrength() {
        assertEquals(OnePair, Strength.from("32T3K"))
        assertEquals(TwoPair, Strength.from("KK677"))
        assertEquals(TwoPair, Strength.from("KTJJT"))
        assertEquals(ThreeOfAKind, Strength.from("T55J5"))
        assertEquals(ThreeOfAKind, Strength.from("QQQJA"))
    }

    @Test
    fun testWildcardHandStrength() {
        assertEquals(OnePair, Strength.from("32T3K", wildcards = true))
        assertEquals(TwoPair, Strength.from("KK677", wildcards = true))
        assertEquals(FourOfAKind, Strength.from("KTJJT", wildcards = true))
        assertEquals(FourOfAKind, Strength.from("T55J5", wildcards = true))
        assertEquals(FourOfAKind, Strength.from("QQQJA", wildcards = true))

        assertEquals(FullHouse, Strength.from("JTT55", wildcards = true))
        assertEquals(FiveOfAKind, Strength.from("JJJJJ", wildcards = true))
        assertEquals(ThreeOfAKind, Strength.from("JJ2AK", wildcards = true))
    }

    @Test
    fun testThreeOfAKind() {
        assertEquals(ThreeOfAKind, Strength.from("T55J5"))
    }

    @Test
    fun testThreeOfAKindWithWildcards() {
        assertEquals(ThreeOfAKind, Strength.from("JJ2AK", wildcards = true))
    }

    @Test
    fun testFiveWildcards() {
        assertEquals(FiveOfAKind, Strength.from("JJJJJ", wildcards = true))
    }
}
