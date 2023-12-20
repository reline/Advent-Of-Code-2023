import kotlin.test.Test
import kotlin.test.assertEquals

class DayTenTest {

    @Test
    fun testDirection() {
        assertEquals(Direction.South, Direction.North.turnAround())
        assertEquals(Direction.East, Direction.North.turnRight())
        assertEquals(Direction.West, Direction.North.turnLeft())
    }

    @Test
    fun testPartOne() {
        val input = """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....
        """.trimIndent()
        assertEquals(4, PipeMaze.partone(input))
    }

    @Test
    fun testPartOneComplex() {
        val input = """
            7-F7-
            .FJ|7
            SJLL7
            |F--J
            LJ.LJ
        """.trimIndent()
        assertEquals(8, PipeMaze.partone(input))
    }

    @Test
    fun testPartTwo() {
        val input = """
            ...........
            .S-------7.
            .|F-----7|.
            .||.....||.
            .||.....||.
            .|L-7.F-J|.
            .|..|.|..|.
            .L--J.L--J.
            ...........
        """.trimIndent()
        assertEquals(4, PipeMaze.parttwo(input))
    }

    @Test
    fun testPartTwoSqueeze() {
        val input = """
            ..........
            .S------7.
            .|F----7|.
            .||....||.
            .||....||.
            .|L-7F-J|.
            .|..||..|.
            .L--JL--J.
            ..........
        """.trimIndent()
        assertEquals(4, PipeMaze.parttwo(input))
    }

    @Test
    fun testPartTwoLarge() {
        val input = """
            .F----7F7F7F7F-7....
            .|F--7||||||||FJ....
            .||.FJ||||||||L7....
            FJL7L7LJLJ||LJ.L-7..
            L--J.L7...LJS7F-7L7.
            ....F-J..F7FJ|L7L7L7
            ....L7.F7||L7|.L7L7|
            .....|FJLJ|FJ|F7|.LJ
            ....FJL-7.||.||||...
            ....L---J.LJ.LJLJ...
        """.trimIndent()
        assertEquals(8, PipeMaze.parttwo(input))
    }

    @Test
    fun testPartTwoJunk() {
        val input = """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJ7F7FJ-
            L---JF-JLJ.||-FJLJJ7
            |F|F-JF---7F7-L7L|7|
            |FFJF7L7F-JF7|JL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L
        """.trimIndent()
        assertEquals(10, PipeMaze.parttwo(input))
    }

    @Test
    fun testPartTwoSimple() {
        val input = """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....
        """.trimIndent()
        assertEquals(1, PipeMaze.parttwo(input))
    }
}