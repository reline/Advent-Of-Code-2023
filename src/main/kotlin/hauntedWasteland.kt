object HauntedWasteland : Solution {
    override fun solve(input: String) {
        println(partone(input))
        println(parttwo(input))
    }

    private fun buildNodes(input: String): Map<String, Node> {
        return input.lines()
            .drop(2)
            .map {
                val (key, value) = it
                    .replace("""\s|\(|\)""".toRegex(), "")
                    .split('=', limit = 2)
                val (left, right) = value.split(',', limit = 2)
                Node(key = key, left = left, right = right)
            }.associateBy { it.key }
    }

    private fun travel(path: String, instructions: List<Instruction>, nodes: Map<String, Node>, predicate: (Node) -> Boolean): Int {
        var stepsTaken = 0
        var currentNode = path
        while (true) {
            for (instruction in instructions) {
                stepsTaken++
                val paths = requireNotNull(nodes[currentNode]) { "Node $currentNode missing" }
                currentNode = when (instruction) {
                    Instruction.L -> paths.left
                    Instruction.R -> paths.right
                }

                if (predicate(requireNotNull(nodes[currentNode]))) {
                    return stepsTaken
                }
            }
        }
    }

    fun partone(input: String): Int {
        val lines = input.lines()
        val instructions = lines.first().map { Instruction.valueOf(it.toString()) }
        val nodes = buildNodes(input)
        return travel(path = "AAA", instructions, nodes) { it.key == "ZZZ" }
    }

    fun parttwo(input: String): Long {
        val instructions = input.lines().first().map { Instruction.valueOf(it.toString()) }
        val nodes = buildNodes(input)
        val startingNodes = nodes.mapNotNull {
            if (it.key.endsWith('A')) it.key else null
        }
        return startingNodes.map { currentNode ->
            travel(currentNode, instructions, nodes) { it.key.endsWith('Z') }.toLong()
        }.reduce(::lcm)
    }

}

enum class Instruction {
    L, R;
}

data class Node(
    val key: String,
    val left: String,
    val right: String,
)

fun lcm(a: Long, b: Long): Long {
    return a / gcd(a, b) * b
}

fun gcd(a: Long, b: Long): Long {
    val d = a % b
    return if (d != 0L) {
        gcd(b, d)
    } else {
        b
    }
}