import kotlin.math.pow

fun main() {

    fun parseCard(input: String): Int {
        val parts = input.trim().split(":")
        val (winningNumbers, ourNumbers) = parts[1].trim().split("|").map { part ->
            part.trim().split(" ").filter { it.matches("\\d+".toRegex()) } //
        }
        return winningNumbers.intersect(ourNumbers.toSet()).size
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val matches = parseCard(it)
            2.0.pow((matches - 1).toDouble()).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val copies = IntArray(input.size)
        copies.fill(1)

        for ((index, value) in input.withIndex()) {
            val cardCount = parseCard(value)

            for (i in 1..cardCount) {
                val nextIndex = index + i
                if (nextIndex >= copies.size) break
                copies[nextIndex] += copies[index]
            }
        }
        return copies.sum()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
