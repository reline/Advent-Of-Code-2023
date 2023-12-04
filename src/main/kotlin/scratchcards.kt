object Scratchcards : Solution {
    override fun solve(input: String) {
        println(partone(input))
        println(parttwo(input))
    }

    fun partone(input: String): Int {
        return input.lines().sumOf {
            points(Card(it))
        }
    }

    private fun points(card: Card): Int {
        return card.scratchedNumbers.fold(0) { acc, number ->
            if (card.winningNumbers.contains(number)) {
                if (acc == 0) {
                    1
                } else {
                    acc * 2
                }
            } else {
                acc
            }
        }
    }

    fun parttwo(input: String): Int {
        val cards = hashMapOf<Int, Int>()
        input.lines().forEachIndexed { cardIndex, line ->
            val cardNumber = cardIndex + 1
            val card = Card(line)
            val cardCount = cards.getOrPut(cardNumber) { 1 }
            val wins = card.scratchedNumbers.count { card.winningNumbers.contains(it) }
            for (i in cardNumber + 1 .. cardNumber + wins) {
                cards[i] = cards.getOrDefault(i, 1) + cardCount
            }
        }
        return cards.values.sum()
    }
}

class Card(s: String) {
    val winningNumbers: Set<Int>
    val scratchedNumbers: List<Int>

    init {
        val (_, numbers) = s.split(':', limit = 2)
        val (winning, scratched) = numbers.split('|', limit = 2)
        winningNumbers = winning.trim().split(' ').filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
        scratchedNumbers = scratched.trim().split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
    }
}