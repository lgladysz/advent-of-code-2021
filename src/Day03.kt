fun main() {
  // test if implementation meets criteria from the description, like:
  val testInput: List<Array<String>> = readInput("Day03_test").map {
    it.split("")
      .drop(1)
      .dropLast(1)
      .toTypedArray()
  }
  check(part1(testInput) == 198)
  check(part2(testInput) == 230)


  val input: List<Array<String>> = readInput("Day03").map {
    it.split("")
      .drop(1)
      .dropLast(1)
      .toTypedArray()
  }
  println(part1(input))
  println(part2(input))
}

private fun part1(input: List<Array<String>>): Int {

  val elementSize: Int = input.first().size
  val gammaRate: Array<String> = Array(elementSize) { "" }
  val epsilonRate: Array<String> = Array(elementSize) { "" }

  for (bit in input.first().indices) {
    val ones = input.count { it[bit] == "1" }
    val zeros = input.count { it[bit] == "0" }

    gammaRate[bit] = when {
      ones >= zeros -> "1"
      else          -> "0"
    }

    epsilonRate[bit] = when {
      ones >= zeros -> "0"
      else          -> "1"
    }
  }

  return gammaRate.toInt() * epsilonRate.toInt()
}

private fun part2(input: List<Array<String>>): Int {
  var oxygenGeneratorRateList: List<Array<String>> = input
  var co2ScrubberRateList: List<Array<String>> = input

  for (bit in input.first().indices) {

    if (oxygenGeneratorRateList.size > 1) {
      val ones = oxygenGeneratorRateList.count { it[bit] == "1" }
      val zeros = oxygenGeneratorRateList.count { it[bit] == "0" }
      oxygenGeneratorRateList = oxygenGeneratorRateList.filter {
        it[bit] == when {
          ones >= zeros -> "1"
          else          -> "0"
        }
      }
    }

    if (co2ScrubberRateList.size > 1) {
      val ones = co2ScrubberRateList.count { it[bit] == "1" }
      val zeros = co2ScrubberRateList.count { it[bit] == "0" }
      co2ScrubberRateList = co2ScrubberRateList.filter {
        it[bit] == when {
          ones >= zeros -> "0"
          else          -> "1"
        }
      }
    }

  }

  val oxygenGeneratorRate = oxygenGeneratorRateList.first().toInt()
  val co2ScrubberRate = co2ScrubberRateList.first().toInt()

  return oxygenGeneratorRate * co2ScrubberRate
}

private fun Array<String>.toInt(): Int {
  return this.joinToString("").toInt(2)
}
