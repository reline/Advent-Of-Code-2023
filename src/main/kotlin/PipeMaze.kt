object PipeMaze : Solution {

    override fun solve(input: String) {
        println(partone(input))
        println(parttwo(input))
    }

    fun partone(input: String): Int {
        return Matrix.from(input.lines()).nest().count() / 2
    }

    fun parttwo(input: String): Int {
        return Matrix.from(input.lines()).enclosedTiles().count()
    }

}

data class Coordinate(val x: Int, val y: Int) {
    val east get() = copy(x = x + 1)
    val west get() = copy(x = x - 1)
    val north get() = copy(y = y - 1)
    val south get() = copy(y = y + 1)

    fun move(direction: Direction) = when (direction) {
        Direction.East -> east
        Direction.West -> west
        Direction.North -> north
        Direction.South -> south
    }
}

enum class Direction {
    North, East, South, West;

    fun turnRight() = rotate(1)
    fun turnLeft() = rotate(3)
    fun turnAround() = rotate(2)

    private fun rotate(aboutFaces: Int) = entries[(ordinal + aboutFaces).rem(entries.size)]
}

data class Matrix(val tiles: List<List<Tile>>) {
    private val verticalBounds = tiles.indices
    private val horizontalBounds = tiles[0].indices

    val allCoordinates = sequence {
        for (y in verticalBounds) {
            for (x in horizontalBounds) {
                yield(Coordinate(x, y))
            }
        }
    }

    val start: Coordinate = allCoordinates.first { tile(it) == Tile.START }

    private val initialDirections = run {
        val west = inBounds(start.west) && tile(start.west) in listOf(Tile.WEST_EAST, Tile.NORTH_EAST, Tile.SOUTH_EAST)
        val east = inBounds(start.east) && tile(start.east) in listOf(Tile.WEST_EAST, Tile.NORTH_WEST, Tile.SOUTH_WEST)
        val north = inBounds(start.north) && tile(start.north) in listOf(Tile.NORTH_SOUTH, Tile.SOUTH_WEST, Tile.SOUTH_EAST)
        val south = inBounds(start.south) && tile(start.south) in listOf(Tile.NORTH_SOUTH, Tile.NORTH_WEST, Tile.NORTH_EAST)
        when {
            west && east -> Pair(Direction.West, Direction.East)
            north && south -> Pair(Direction.North, Direction.South)
            south && west -> Pair(Direction.South, Direction.West)
            south && east -> Pair(Direction.South, Direction.East)
            north && west -> Pair(Direction.North, Direction.West)
            north && east -> Pair(Direction.North, Direction.West)
            else -> throw IllegalArgumentException("Bad input")
        }
    }

    fun inBounds(coordinate: Coordinate): Boolean {
        return coordinate.x in horizontalBounds && coordinate.y in verticalBounds
    }

    fun tile(coordinate: Coordinate) = tiles[coordinate.y][coordinate.x]

    fun nest() = sequence {
        var direction = initialDirections.first
        var coords = start
        var tile: Tile

        do {
            coords = coords.move(direction)
            tile = tile(coords)
            yield(coords)
            if (tile != Tile.START) {
                direction = tile.getDirection(direction)
            }
        } while (tile != Tile.START)
    }

    fun enclosedTiles() = sequence {
        val nest = nest().toSet()
        allCoordinates.filterNot { it in nest }.forEach { coordinate ->
            var intersects = 0
            for (offset in coordinate.x + 1..horizontalBounds.last) {
                val ray = Coordinate(offset, coordinate.y)
                if (ray in nest && tile(ray) !in setOf(Tile.WEST_EAST, Tile.NORTH_WEST, Tile.NORTH_EAST)) {
                    intersects++
                }
            }
            if (intersects % 2 == 1) yield(coordinate)
        }
    }

    companion object {
        fun from(input: List<String>) = Matrix(input.map { line -> line.map { Tile.from(it) } })
    }
}

enum class Tile(val char: Char, val connections: Set<Direction>) {
    NORTH_SOUTH('|', setOf(Direction.North, Direction.South)),
    NORTH_WEST('J', setOf(Direction.North, Direction.West)),
    NORTH_EAST('L', setOf(Direction.North, Direction.East)),
    SOUTH_EAST('F', setOf(Direction.South, Direction.East)),
    SOUTH_WEST('7', setOf(Direction.South, Direction.West)),
    WEST_EAST('-', setOf(Direction.West, Direction.East)),
    START('S', emptySet()),
    EMPTY('.', emptySet());

    fun getDirection(heading: Direction) = connections.first { it != heading.turnAround() }

    companion object {
        fun from(char: Char) = entries.first { it.char == char }
    }
}
