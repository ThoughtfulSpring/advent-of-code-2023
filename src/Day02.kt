fun main() {
    fun parseGame(input: String): Pair<String, List<Map<String, Int>>> {
        val parts = input.trim().split(":")
        val (_, gameId) = parts[0].trim().split(" ")
        val sets = parts[1].trim().split(";")

        val cubeList = mutableListOf<Map<String, Int>>()
        for (set in sets) {
            val cubeQuantities = set.trim().split(",").associate {
                val (quantity, color) = it.trim().split(" ")
                color to quantity.toInt()
            }
            cubeList.add(cubeQuantities)
        }

        return gameId to cubeList
    }

    fun checkQuantityCondition(cubeQuantities: Map<String, Int>, quantityCondition: Map<String, Int>): Boolean {
        return cubeQuantities.all { (color, quantity) ->
            quantity <= (quantityCondition[color] ?: 0)
        }
    }

    fun part1(input: List<String>): Int {
        val quantityCondition = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        val gamesMap = input.mapNotNull { game ->
            val (gameId, cubeList) = parseGame(game)
            if (cubeList.all { checkQuantityCondition(it, quantityCondition) }) {
                gameId to cubeList
            } else {
                null
            }
        }.toMap()

        return gamesMap.keys.sumOf { it.toInt() }
    }

    fun getMinimumRequirement(cubeList: List<Map<String, Int>>): Map<String, Int> {
        val highestValues = mutableMapOf<String, Int>()
        for (cube in cubeList) {
            for ((key, value) in cube) {
                highestValues[key] = maxOf(highestValues[key] ?: 0, value)
            }
        }
        return highestValues
    }

    fun calculateProductOfValues(map: Map<String, Int>): Long {
        return map.values.fold(1L) { acc, value -> acc * value }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { game ->
            val (_, cubeList) = parseGame(game)
            calculateProductOfValues(getMinimumRequirement(cubeList))
        }.toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
