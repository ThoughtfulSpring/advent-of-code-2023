fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val numberString = line.filter { it.isDigit() }

            when (numberString.length) {
                0 -> 0
                1 -> (numberString.repeat(2)).toInt()
                else -> {
                    val concatenatedDigits = numberString.first() + numberString.last().toString()
                    concatenatedDigits.toInt()
                }
            }
        }
    }

    fun extractNumbers(input: String): String {
        val numberWordsToDigits = mapOf(
            "zero" to "0",
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        var result = ""

        for(i in input.indices) {
            if(input[i].isDigit()){
                result += input[i].toString()
            } else {
                numberWordsToDigits.forEach {( word, digit ) ->
                    if(input.startsWith(word, i)) {
                        result += digit
                    }
                }
            }
        }
        return result
    }


    fun part2(input: List<String>): Int {
        return input.map { line ->
            extractNumbers(line)
        }.let {
            part1(it)
        }
    }


// test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("Day01_part1_test")
    val testInputPart2 = readInput("Day01_part2_test")
    check(part1(testInputPart1) == 142)
    check(part2(testInputPart2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
