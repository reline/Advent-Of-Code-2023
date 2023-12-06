object WaitForIt : Solution {
    override fun solve(input: String) {
        println(partone(input))
        println(parttwo(input))
    }

    fun partone(input: String): Int {
        val lines = input.lines()
        val times = lines.first().split(' ').filter { it.isNotEmpty() }.drop(1).map { it.toInt() }
        val distances = lines.last().split(' ').filter { it.isNotEmpty() }.drop(1).map { it.toInt() }
        return times.foldIndexed(1) { i, acc, raceLength ->
            val record = distances[i]
            val absoluteMinimumTime = record / raceLength + 1
            var winConditions = 0
            for (time in absoluteMinimumTime..raceLength - absoluteMinimumTime) {
                if ((raceLength - time) * time > record) {
                    winConditions++
                }
            }
            acc * winConditions
        }
    }

    fun parttwo(input: String): Long {
        val lines = input.lines()
        val raceLength = lines.first().split(' ').filter { it.isNotEmpty() }.drop(1).joinToString(separator = "").toLong()
        val record = lines.last().split(' ').filter { it.isNotEmpty() }.drop(1).joinToString(separator = "").toLong()
        val absoluteMinimumTime = record / raceLength + 1
        var winConditions = 0L
        for (time in absoluteMinimumTime..raceLength - absoluteMinimumTime) {
            if ((raceLength - time) * time > record) {
                winConditions++
            }
        }
        return winConditions
    }

}