import Direction.down
import Direction.forward
import Direction.up


fun main() {

  fun part1(input: List<Coordinates>): Int {
    var positionVertical = 0
    var positionHorizontal = 0

    input.forEach {
      when (it.direction) {
        forward -> positionHorizontal += it.move
        up      -> positionVertical -= it.move
        down    -> positionVertical += it.move
      }
    }

    return positionVertical * positionHorizontal
  }

  fun part2(input: List<Coordinates>): Int {
    var positionVertical = 0
    var positionHorizontal = 0
    var aim = 0

    input.forEach {
      when (it.direction) {
        forward -> {
          positionHorizontal += it.move
          positionVertical += aim * it.move
        }
        up      -> aim -= it.move
        down    -> aim += it.move
      }
    }

    return positionVertical * positionHorizontal
  }

  // test if implementation meets criteria from the description, like:
  val testInput: List<Coordinates> = readInput("Day02_test").toCoordinates()
  check(part1(testInput) == 150)
  check(part2(testInput) == 900)

  val input: List<Coordinates> = readInput("Day02").toCoordinates()
  println(part1(input))
  println(part2(input))

}

private data class Coordinates(val direction: Direction, val move: Int)

private enum class Direction {
  forward, up, down
}

private fun List<String>.toCoordinates(): List<Coordinates> {
  return this.map { it.split(" ") }.map { (a, b) -> Coordinates(Direction.valueOf(a), b.toInt()) }
}
