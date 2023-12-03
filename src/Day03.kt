fun main() {

    fun Char.isSymbol(): Boolean = this != '.' && !isDigit()

    fun parseData(input: List<String>): Int {
        val rows = input.size
        val cols = input[0].lastIndex
        val numbersFoundList: MutableList<Int> = mutableListOf()

        for (ni in 0 until rows) {
            for (match in """\d+""".toRegex().findAll(input[ni])) {
                val minRange = (match.range.first - 1).coerceAtLeast(0)
                val maxRange = (match.range.last + 1).coerceAtMost(cols)
                var addNumber = false

                // Check above
                val topLane = ni - 1
                if (topLane > 0) {
                    for (ti in minRange..maxRange) {
                        if (input[topLane][ti].isSymbol()) {
                            addNumber = true
                        }
                    }
                }
                // Check below
                val botLane = ni + 1
                if (botLane < rows) {
                    for (ti in minRange..maxRange) {
                        if (input[botLane][ti].isSymbol()) {
                            addNumber = true
                        }
                    }
                }
                // Check sides
                if (input[ni][minRange].isSymbol() || input[ni][maxRange].isSymbol()) {
                    addNumber = true
                }

                if (addNumber) {
                    numbersFoundList.add(match.value.toInt())
                }
            }
        }
//        numbersFoundList.println()
        return numbersFoundList.sumOf { it }
    }


    fun part1(input: List<String>): Int {
        return parseData(input)
    }


    fun part2(input: List<String>): Int {

        return input.size
    }


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

