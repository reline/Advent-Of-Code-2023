object GearRatios : Solution {
    override fun solve(input: String) {
        println(partone(input))
        println(parttwo(input))
    }

    fun partone(input: String): Int {
        val matrix = input.lines().map { it.toCharArray() }
        val partNumbers = mutableListOf<Int>()
        matrix.forEachIndexed { y, chars ->
            chars.forEachIndexed { x, c ->
                if (c != '.' && !c.isDigit()) {
                    partNumbers += findAdjacentPartNumbers(Pair(x, y), matrix, erasePartNumbers = true)
                }
            }
        }
        return partNumbers.sum()
    }

    fun parttwo(input: String): Int {
        val matrix = input.lines().map { it.toCharArray() }
        val gearRatios = mutableListOf<Int>()
        matrix.forEachIndexed { y, chars ->
            chars.forEachIndexed { x, c ->
                if (c == '*') {
                    val adjacentPartNumbers = findAdjacentPartNumbers(Pair(x, y), matrix)
                    if (adjacentPartNumbers.size == 2) {
                        gearRatios += adjacentPartNumbers.first() * adjacentPartNumbers.last()
                    }
                }
            }
        }
        return gearRatios.sum()
    }
}

private fun findAdjacentPartNumbers(coordinate: Pair<Int, Int>, matrix: List<CharArray>, erasePartNumbers: Boolean = false): Set<Int> {
    val height = matrix.size
    val width = matrix[0].size
    val partNumbers = mutableSetOf<Int>()

    for (i in -1.. 1) {
        val x = coordinate.first + i
        if (x < 0 || x >= width) continue

        for (j in  -1..1) {
            val y = coordinate.second + j
            if (y < 0 || y >= height) continue

            if (matrix[y][x].isDigit()) {
                partNumbers += determinePartNumber(Pair(x, y), matrix, erase = erasePartNumbers)
            }
        }
    }

    return partNumbers
}

private fun determinePartNumber(pair: Pair<Int, Int>, matrix: List<CharArray>, erase: Boolean): Int {
    val row = matrix[pair.second]
    var partNumber = row[pair.first].toString()
    for (i in pair.first - 1 downTo 0) {
        if (row[i].isDigit()) {
            partNumber = row[i] + partNumber
            if (erase) {
                row[i] = '.'
            }
        } else {
            break
        }
    }
    for (i in pair.first + 1..< row.size) {
        if (row[i].isDigit()) {
            partNumber += row[i]
            if (erase) {
                row[i] = '.'
            }
        } else {
            break
        }
    }
    return partNumber.toInt()
}