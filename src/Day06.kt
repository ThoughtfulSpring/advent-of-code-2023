fun main() {

    fun parseData(input: List<String>): List<Race> {
        val timeList = input[0].split(":")[1].trim().split(" ").filter { it.matches("\\d+".toRegex()) }
        val distanceList = input[1].split(":")[1].trim().split(" ").filter { it.matches("\\d+".toRegex()) }
        val result = mutableListOf<Race>()
        for (i in timeList.indices) {
            val race = Race(time = timeList[i].toLong(), distance = distanceList[i].toLong())
            result.add(race)
        }
        return result
    }

    fun getRaceOptions(race: Race): List<Long> {
        val result = mutableListOf<Long>()
        for (i in 1 until race.time) {
            val totalDistance = i * (race.time - i)
            result.add(totalDistance)
        }
        return result
    }

    fun getBestRaceOptions(raceOptions: List<Long>, recordDistance: Long): List<Long> {
        return raceOptions.filter { it > recordDistance }
    }

    fun part1(input: List<String>): Int {
        val parsedData = parseData(input)
        var result = 1
        parsedData.forEach { race ->
            result *= getBestRaceOptions(getRaceOptions(race), race.distance).size
        }
        return result
    }

    fun getNewRaceData(races: List<Race>): Race {
        var newTime = ""
        for (i in races.indices) {
            newTime += races[i].time
        }
        var newDistance = ""
        for (i in races.indices) {
            newDistance += races[i].distance
        }
        return Race(time = newTime.toLong(), distance = newDistance.toLong())
    }

    fun lowerBound(race: Long, record: Long): Long {
        var lower = 0L
        var distance = 0L
        while (distance < record) {
            lower++
            distance = lower * (race - lower)
        }
        return lower
    }

    fun upperBound(race: Long, record: Long): Long {
        var upper = race
        var distance = 0L
        while (distance < record) {
            upper--
            distance = upper * (race - upper)
        }
        return upper
    }

    fun part2(input: List<String>): Long {
        val parsedData = parseData(input)
        val race = getNewRaceData(parsedData)

        return (upperBound(race.time, race.distance) - lowerBound(
            race.time,
            race.distance
        )) + 1
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

data class Race(
    val time: Long, // in milliseconds
    val distance: Long
)