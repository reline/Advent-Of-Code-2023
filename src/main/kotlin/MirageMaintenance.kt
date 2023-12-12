object MirageMaintenance : Solution {
    override fun solve(input: String) {
        println(partone(input))
        println(parttwo(input))
    }

    fun partone(input: String): Int {
        return input.lines().sumOf { line ->
            line.split(' ')
                .map { it.toInt() }
                .nextValue()
        }
    }

    fun parttwo(input: String): Int {
        return input.lines().sumOf { line ->
            line.split(' ')
                .map { it.toInt() }
                .previousValue()
        }
    }
}

private fun List<Int>.extrapolate(operation: (List<Int>, Int) -> Int): Int {
    if (isEmpty()) return 0

    val diffs = mapIndexedNotNull { index, value ->
        getOrNull(index + 1)?.let { it - value }
    }
    return if (diffs.all { it == 0 }) {
        operation(this, 0)
    } else {
        operation(this, diffs.extrapolate(operation))
    }
}

fun List<Int>.nextValue() = extrapolate { history, diff -> history.last() + diff }

fun List<Int>.previousValue() = extrapolate { history, diff -> history.first() - diff }
