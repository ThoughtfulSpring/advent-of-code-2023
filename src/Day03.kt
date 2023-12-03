fun main() {

    fun Char.isSymbol(): Boolean = this != '.' && !isDigit()

    fun parseData(input: List<String>): Map<Pair<Int, Int>, List<Int>> {
        val rows = input.size
        val cols = input[0].lastIndex
        // Map to store symbol coordinates and adjacent numbers
        val symbolCoordinatesMap =  mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

        for (ni in 0 until rows) {
            for (match in """\d+""".toRegex().findAll(input[ni])) {
                val number = match.value.toInt()
                val minRange = (match.range.first - 1).coerceAtLeast(0)
                val maxRange = (match.range.last + 1).coerceAtMost(cols)
                val topLane = ni - 1
                val botLane = ni + 1

                for (ti in minRange..maxRange) {
                    // Check above
                    if (topLane > 0 && input[topLane][ti].isSymbol()) {
                        val symbolCoordinate = Pair(topLane, ti)
                        symbolCoordinatesMap.computeIfAbsent(symbolCoordinate) { mutableListOf() }
                        symbolCoordinatesMap[symbolCoordinate]?.add(number)
                    }
                    // Check below
                    if (botLane < rows && input[botLane][ti].isSymbol()) {
                        val symbolCoordinate = Pair(botLane, ti)
                        symbolCoordinatesMap.computeIfAbsent(symbolCoordinate) { mutableListOf() }
                        symbolCoordinatesMap[symbolCoordinate]?.add(number)
                    }

                    // Check sides
                    if (input[ni][ti].isSymbol()) {
                        val symbolCoordinate = Pair(ni, ti)
                        symbolCoordinatesMap.computeIfAbsent(symbolCoordinate) { mutableListOf() }
                        symbolCoordinatesMap[symbolCoordinate]?.add(number)
                    }
                }
            }
        }
        symbolCoordinatesMap.println()
        return symbolCoordinatesMap
    }

    fun part1(input: List<String>): Int {
        return parseData(input).values.sumOf { it.sum() }
    }

    fun part2(input: List<String>): Int {
        return parseData(input).values.sumOf {
            if (it.size > 1) {
                it.fold(1) { acc, value -> acc * value }.toInt()
            } else {
                0
            }
        }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
