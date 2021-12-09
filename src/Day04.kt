fun main() {
  // test if implementation meets criteria from the description, like:
  val testInputNumbers: List<Int> = readInput("Day04_test").extractNumbers()
  val testInputBingo: List<List<List<Int>>> = readInput("Day04_test").extractTables()

  check(part1(testInputNumbers, testInputBingo) == 4512)
  check(part2(testInputNumbers, testInputBingo) == 1924)


  val inputNumbers: List<Int> = readInput("Day04").extractNumbers()
  val inputBingo: List<List<List<Int>>> = readInput("Day04").extractTables()

  println(part1(inputNumbers, inputBingo))
  println(part2(inputNumbers, inputBingo))
}


private fun part1(inputNumbers: List<Int>, inputBingo: List<List<List<Int>>>): Int {
  val bingoTables: List<BingoTable> = inputBingo.toBingoTable()

  for (number in inputNumbers) {
    for (table in bingoTables) {
      table.markElementsWithNumber(number)

      if (table.isWinner()) {
        return number * table.sumUnmarkedElements()
      }
    }
  }
  return 0
}

private fun part2(inputNumbers: List<Int>, inputBingo: List<List<List<Int>>>): Int {
  val bingoTables: List<BingoTable> = inputBingo.toBingoTable()
  val winnersArray: Array<Boolean> = Array(bingoTables.size) { false }

  for (number in inputNumbers) {
    for ((index, table) in bingoTables.withIndex()) {

      table.markElementsWithNumber(number)

      if (table.isWinner()) {
        winnersArray[index] = true
        if (winnersArray.count { it } == winnersArray.size) {
          return number * table.sumUnmarkedElements()
        }
      }

    }
  }
  return 0
}

private fun List<String>.extractNumbers(): List<Int> {
  return this[0].split(",").map { it.toInt() }
}

private fun List<String>.extractTables(): List<List<List<Int>>> {
  return this.drop(2).filter { it.isNotEmpty() }.windowed(size = 5,
    step = 5,
    transform = { row -> row.map { it.windowed(2, 3) }.map { strings -> strings.map { it.trim().toInt() } } })
}

private fun List<List<List<Int>>>.toBingoTable(): List<BingoTable> {
  return this.map { lists -> lists.map { strings -> strings.map { it to false } } } as List<BingoTable>
}

private fun BingoTable.sumUnmarkedElements(): Int {
  val unmarkedElements = this.map { row -> row.filter { !it.second } }
  return unmarkedElements.map { pairs -> pairs.map { it.first } }.sumOf { it.sum() }
}

private fun BingoTable.isWinner(): Boolean {
  for (row in this.indices) {
    if (this[row].count { it.second } == this.size) {
      return true
    }
    var tmp = 0
    for (column in this[row].indices) {
      if (this[column][row].second) tmp++
    }
    if (tmp == this.size) {
      return true
    }
  }
  return false
}

private fun BingoTable.markElementsWithNumber(number: Int) {
  for (row in this) {
    for ((index, element) in row.withIndex()) {
      if (element.first == number) {
        row[index] = element.copy(element.first, true)
      }
    }
  }
}

private typealias BingoTable = MutableList<MutableList<Pair<Int, Boolean>>>
