import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

fun main(args: Array<String>) {
    Day.valueOf(args.first()).solve()
}

interface Solution {
    fun solve(input: String)
}

enum class Day(private val solution: Solution) {

    One(Trebuchet);

    fun solve() {
        FileSystem.RESOURCES.source("$name.txt".toPath()).buffer().use {
            solution.solve(it.readUtf8())
        }
    }
}