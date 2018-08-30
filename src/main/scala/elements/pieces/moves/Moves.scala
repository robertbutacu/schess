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
    val yAxis = to.Y - from.X

    (xAxis, yAxis) match {
      case (down, left) if down < 0 && left < 0 => diagonalLeftDown
      case (down, right) if down < 0 && right > 0 => diagonalRightDown
      case (up, left) if up > 0 && left < 0 => diagonalLeftUp
      case (up, right) if up > 0 && right > 0 => diagonalRightUp
      case (straight, right) if straight == 0 && right > 0 => straightRight
      case (straight, left) if straight == 0 && left < 0 => straightLeft
      case (up, straight) if up > 0 && straight == 0 => straightUp
      case (down, straight) if down < 0 && straight == 0 => straightDown
    }
  }
}
