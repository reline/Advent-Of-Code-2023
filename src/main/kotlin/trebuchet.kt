object Trebuchet : Solution {
    override fun solve(input: String) {
        println(partone(input))
        println(parttwo(input))
    }

    fun partone(input: String): Int {
        return input.lines().sumOf { line ->
            "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt()
        }
    }

    fun parttwo(input: String): Int {
        return input.lines().sumOf { line ->
            "${line.firstDigit()}${line.lastDigit()}".toInt()
        }
    }

    private val digits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    private fun String.firstDigit(): Int {
        forEachIndexed { index, c ->
            if (c.isDigit()) {
                return c.digitToInt()
            }
            digitForRegion(index)?.let {
                return it
            }
        }
        throw IllegalArgumentException("$this does not contain a digit")
    }

    private fun String.lastDigit(): Int {
        for (index in length - 1 downTo 0) {
            val c = this[index]
            if (c.isDigit()) {
                return c.digitToInt()
            }
            digitForRegion(index)?.let {
                return it
            }
        }
        throw IllegalArgumentException("$this does not contain a digit")
    }

    private fun CharSequence.digitForRegion(index: Int): Int? {
        digits.entries.forEach {
            if (regionMatches(index, it.key, 0, it.key.length)) {
                return it.value
            }
        }
        return null
    }
}
