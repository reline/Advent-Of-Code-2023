object CubeConundrum : Solution {
    override fun solve(input: String) {
        println(partone(input, Colors(red = 12, green = 13, blue = 14)))
        println(parttwo(input))
    }

    fun partone(input: String, colors: Colors): Int {
        return input.games().sumOf { game ->
            val possible = game.sets.all { set ->
                set.blue <= colors.blue && set.red <= colors.red && set.green <= colors.green
            }

            if (possible) game.id else 0
        }
    }

    fun parttwo(input: String): Int {
        return input.games().sumOf { game ->
            val result = game.sets.reduce { acc, set ->
                max(acc, set)
            }
            result.red * result.blue * result.green
        }
    }
}

data class Game(val id: Int, val sets: List<Colors>)

private fun String.games(): List<Game> {
    return lines().map { line ->
        val (label, record) = line.split(':', limit = 2)
        val (_, id) = label.split(' ', limit = 2)
        val sets = record.split(';').map { set ->
            val subsets = set.split(',')
            subsets.map { subset ->
                val (count, color) = subset.trim().split(' ')
                Color.valueOf(color).colors(count.toInt())
            }.reduce { acc, subset ->
                acc + subset
            }
        }
        Game(id.toInt(), sets)
    }
}

enum class Color {
    red, green, blue;

    fun colors(count: Int): Colors {
        return when (this) {
            red -> Colors(red = count)
            green -> Colors(green = count)
            blue -> Colors(blue = count)
        }
    }
}

data class Colors(
    val red: Int = 0,
    val blue: Int = 0,
    val green: Int = 0,
) {
    operator fun plus(colors: Colors): Colors {
        return Colors(
            red + colors.red,
            blue + colors.blue,
            green + colors.green,
        )
    }
}

fun max(left: Colors, right: Colors): Colors {
    return Colors(
        red = maxOf(left.red, right.red),
        green = maxOf(left.green, right.green),
        blue = maxOf(left.blue, right.blue),
    )
}