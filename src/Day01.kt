fun main() {
  fun part1(input: List<Int>): Int {
    var result = 0
    var previousNumber = input.first()

    for (currentNumber in input) {
      if (currentNumber > previousNumber) {
        result += 1
      }
      previousNumber = currentNumber
    }

    return result
  }

  fun part2(input: List<List<Int>>): Int {
    var result = 0
    var previousNumbers = input.first()

    for (currentNumbers in input) {
      if (currentNumbers.sum() > previousNumbers.sum()) {
        result += 1
      }
      previousNumbers = currentNumbers
    }

    return result
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day01_test").map { it.toInt() }
  check(part1(testInput) == 7)
  check(part2(testInput.windowed(3)) == 5)

  val input = readInput("Day01").map { it.toInt() }
  println(part1(input))
  println(part2(input.windowed(3)))
}
