fun main() {


  // test if implementation meets criteria from the description, like:
  val testInput: List<PointRange> = readInput("Day05_test").toPointRangeList()
  check(10.part1(testInput) == 5)
  check(10.part2(testInput) == 12)

  val input: List<PointRange> = readInput("Day05").toPointRangeList()
  println(1000.part1(input))
  println(1000.part2(input))
}

private fun Int.part1(input: List<PointRange>): Int {
  val plot: Plot = createEmptyPlot(this)
  val list = input.filter { it.first.first == it.second.first || it.first.second == it.second.second }

  list.forEach { plot.markLane(it) }

  return plot.countDanger()
}

private fun Int.part2(input: List<PointRange>): Int {
  val plot: Plot = createEmptyPlot(this)

  input.forEach { plot.markLane(it) }

  return plot.countDanger()
}

private fun List<String>.toPointRangeList(): List<PointRange> {
  return this.map { s -> s.split(" -> ") }.map { PointRange(it.first().toPoint(), it.last().toPoint()) }
}

private fun Plot.markLane(range: PointRange) {
  with(range) {
    val xRange: IntProgression =
      if (first.first < second.first) {
        first.first.rangeTo(second.first)
      } else {
        first.first.downTo(second.first)
      }

    val yRange: IntProgression =
      if (first.second < second.second) {
        first.second.rangeTo(second.second)
      } else {
        first.second.downTo(second.second)
      }

    if (first.first == second.first || first.second == second.second) {
      for (x in xRange) {
        for (y in yRange) {
          this@markLane[x][y]++
        }
      }
    } else {
      val xIterator = xRange.iterator()
      val yIterator = yRange.iterator()
      while (xIterator.hasNext()) {
        this@markLane[xIterator.nextInt()][yIterator.nextInt()]++
      }
    }
  }
}

private fun Plot.countDanger(): Int {
  return this.sumOf { x -> x.count { it >= 2 } }
}

private fun createEmptyPlot(xy: Int): Plot = createEmptyPlot(xy, xy)

private fun createEmptyPlot(x: Int, y: Int): Plot = Array(x) { Array(y) { 0 } }

private fun String.toPoint(): Point {
  return this.trim().split(",").let { Point(it[0].toInt(), it[1].toInt()) }
}

private typealias Plot = Array<Array<Int>>

private typealias Point = Pair<Int, Int>

private typealias PointRange = Pair<Point, Point>



