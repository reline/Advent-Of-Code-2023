object CamelCards : Solution {
    override fun solve(input: String) {
        println(partone(input))
        println(parttwo(input))
    }

    fun partone(input: String): Int {
        return input.lines()
            .map { line ->
                val (cards, bid) = line.split(' ', limit = 2)
                Hand(cards, Strength.from(cards), bid.toInt())
            }
            .sorted()
            .mapIndexed { index, hand ->
                val rank = index + 1
                hand.bid * rank
            }
            .sum()
    }

    fun parttwo(input: String): Int {
        return input.lines()
            .map { line ->
                val (cards, bid) = line.split(' ', limit = 2)
                Hand(cards, Strength.from(cards, wildcards = true), bid.toInt())
            }
            .sortedWith(Hand.jokerComparator)
            .mapIndexed { index, hand ->
                val rank = index + 1
                hand.bid * rank
            }.sum()
    }
}

typealias Cards = String
typealias Card = Char

/**
 * @param cards A list of 5 cards labeled one of A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2
 */
data class Hand(val cards: Cards, val strength: Strength, val bid: Int): Comparable<Hand> {
    override operator fun compareTo(other: Hand) = defaultComparator.compare(this, other)

    companion object {
        private class HandComparator(val suiteComparator: Comparator<Suite>) : Comparator<Hand> {
            override fun compare(hand: Hand, other: Hand): Int {
                if (hand.strength != other.strength) {
                    return hand.strength.compareTo(other.strength)
                }

                hand.cards.forEachIndexed { i, card ->
                    val otherCard = other.cards[i]
                    if (card != otherCard) {
                        val suite = Suite.from(card)
                        val otherSuite = Suite.from(otherCard)
                        return suiteComparator.compare(suite, otherSuite)
                    }
                }

                return 0
            }
        }

        private val defaultComparator = HandComparator { suite, other ->
            suite.compareTo(other)
        }

        val jokerComparator: Comparator<Hand> = HandComparator { suite, other ->
            Suite.jokerComparator.compare(suite, other)
        }
    }
}

enum class Strength {
    HighCard, OnePair, TwoPair, ThreeOfAKind, FullHouse, FourOfAKind, FiveOfAKind;

    companion object {
        fun from(cards: Cards, wildcards: Boolean = false): Strength {
            require(cards.length == 5) { "A hand consists of five cards" }

            val joker = Suite.J.toString().first()
            val (jokers, remainingCards) = cards.partition { wildcards && it == joker }
            val groups = remainingCards.groupBy { it }
            val largestGroup = if (groups.values.isNotEmpty()) groups.values.maxOf { it.size } else 0
            val duplicates = largestGroup + jokers.length

            return when (groups.size) {
                5 -> HighCard
                4 -> OnePair
                3 -> if (duplicates == 3) ThreeOfAKind else TwoPair
                2 -> if (duplicates == 4) FourOfAKind else FullHouse
                else -> FiveOfAKind
            }
        }
    }
}

enum class Suite {
    Two, Three, Four, Five, Six, Seven, Eight, Nine, T, J, Q, K, A;

    companion object {
        fun from(card: Card): Suite {
            return when (card) {
                '2' -> Two
                '3' -> Three
                '4' -> Four
                '5' -> Five
                '6' -> Six
                '7' -> Seven
                '8' -> Eight
                '9' -> Nine
                else -> Suite.valueOf(card.toString())
            }
        }

        /**
         * Returns zero if this object is equal to the specified other object, a negative number if it's less than
         * other, or a positive number if it's greater than other.
         */
        val jokerComparator: Comparator<Suite> = Comparator { suite, other ->
            when {
                suite == other -> 0
                suite == J -> -1
                other == J -> 1
                else -> suite.compareTo(other)
            }
        }
    }
}
