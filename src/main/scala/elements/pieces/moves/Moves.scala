package elements.pieces.moves

import actions.PiecePosition

object Moves {
  def diagonalLeftUp = (X: Int, Y: Int) => (X - 1, Y + 1)

  def diagonalRightUp = (X: Int, Y: Int) => (X + 1, Y + 1)

  def diagonalLeftDown = (X: Int, Y: Int) => (X - 1, Y - 1)

  def diagonalRightDown = (X: Int, Y: Int) => (X + 1, Y - 1)

  def straightUp = (X: Int, Y: Int) => (X, Y + 1)

  def straightDown = (X: Int, Y: Int) => (X, Y - 1)

  def straightLeft = (X: Int, Y: Int) => (X - 1, Y)

  def straightRight = (X: Int, Y: Int) => (X + 1, Y)

  def moveType(from: PiecePosition, to: PiecePosition): (Int, Int) => (Int, Int) = {
    val xAxis = to.X - from.X
    val yAxis = to.Y - from.Y

    (xAxis, yAxis) match {
      case (left, down) if down < 0 && left < 0 => diagonalLeftDown
      case (right, down) if down < 0 && right > 0 => diagonalRightDown
      case (left, up) if up > 0 && left < 0 => diagonalLeftUp
      case (right, up) if up > 0 && right > 0 => diagonalRightUp
      case (straight, up) if straight == 0 && up > 0 => straightUp
      case (straight, down) if straight == 0 && down < 0 => straightDown
      case (right, straight) if right > 0 && straight == 0 => straightRight
      case (left, straight) if left < 0 && straight == 0 => straightLeft
    }
  }
}
