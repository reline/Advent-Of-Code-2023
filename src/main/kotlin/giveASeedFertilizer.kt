object Fertilizer : Solution {
    override fun solve(input: String) {
        println(partone(input))
        println(parttwo(input))
    }

    private fun String.toAlmanac(): Almanac {
        // was having some wonky issues with "\n\n" not splitting properly, quick hack
        return lines().joinToString(separator = "\n") {
            it.ifBlank { "newline" }
        }.split("newline")
    }

    private fun Almanac.seeds(): List<Long> {
        return first().trim().split(' ').mapNotNull { it.toLongOrNull() }
    }

    private fun Almanac.seedRanges(): List<Range> {
        val seeds = seeds()
        return List(seeds.size / 2) {
            val i = it * 2
            Range(start = seeds[i], length = seeds[i + 1])
        }
    }

    private fun Almanac.maps(): List<AlmanacMap> {
        return drop(1).map {
            val lines = it.split('\n').filter { line -> line.isNotBlank() }.drop(1)
            lines.map { line ->
                val (destination, source, length) = line.split(' ', limit = 3).map { number -> number.toLong() }
                GardenMapper(destination = destination, source = source, length = length)
            }
        }
    }

    private fun List<AlmanacMap>.findLocation(seed: Long): Long {
        var category = seed
        forEach { ranges ->
            val range = ranges.find { it.contains(category) }
            category = range?.map(category) ?: category
        }
        return category
    }

    fun partone(input: String): Long {
        val almanac = input.toAlmanac()
        val seeds = almanac.seeds()
        val maps = almanac.maps()
        return seeds.minBy { maps.findLocation(it) }
    }

    fun parttwo(input: String): Long {
        val almanac = input.toAlmanac()
        val seedRanges = almanac.seedRanges()
        val maps = almanac.maps()

        return maps.scan(seedRanges) { ranges, map ->
            ranges.flatMap { seedRange ->
                val evaluatedRanges = map.fold(EvaluatedRanges(unmapped = listOf(seedRange))) { ranges, line ->
                    val newRanges = ranges.unmapped.fold(EvaluatedRanges()) { acc, range ->
                        val (newRange, unmappedRanges) = line.map(range)
                        val mapped = if (newRange != null) {
                            acc.mapped + newRange
                        } else {
                            acc.mapped
                        }
                        EvaluatedRanges(mapped = mapped, unmapped = acc.unmapped + unmappedRanges)
                    }
                    newRanges.copy(mapped = ranges.mapped + newRanges.mapped)
                }
                evaluatedRanges.mapped + evaluatedRanges.unmapped
            }
        }.last().map { it.start }.min()
    }
}

typealias Almanac = List<String>

typealias AlmanacMap = List<GardenMapper>

data class Range(val start: Long, val length: Long) {
    val end: Long by lazy { start + length }
}

data class GardenMapper(val source: Long, val destination: Long, val length: Long) {
    fun contains(number: Long): Boolean {
        return number >= source && number < source + length
    }

    fun overlaps(range: Range): Boolean {
        return range.start < source + length && range.end > source
    }

    fun map(number: Long): Long {
        return destination + number - source
    }

    data class EvaluatedRange(val newRange: Range? = null, val unmappedRanges: List<Range>)
    fun map(range: Range): EvaluatedRange {
        if (!overlaps(range)) {
            return EvaluatedRange(unmappedRanges = listOf(range))
        }

        var start = destination
        var len = length
        val containsStart = contains(range.start)
        val containsEnd = contains(range.end - 1)
        val unmappedRanges = mutableListOf<Range>()

        if (containsStart) {
            start = map(range.start)
            if (containsEnd) {
                len = range.length
            }
        } else {
            unmappedRanges.add(Range(start = range.start, length = source - range.start))
        }

        if (containsEnd) {
            if (!containsStart) {
                len = range.end - source
            }
        } else {
            val end = source + length
            unmappedRanges.add(Range(start = end, length = range.end - end))
            if (containsStart) {
                len = end - range.start
            }
        }

        return EvaluatedRange(
            newRange = Range(start, length = len),
            unmappedRanges = unmappedRanges,
        )
    }
}

data class EvaluatedRanges(val mapped: List<Range> = emptyList(), val unmapped: List<Range> = emptyList())