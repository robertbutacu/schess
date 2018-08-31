package actions

import elements.boards.BoardUtils

import scala.util.{Failure, Success, Try}

case class PiecePosition(X: Int, Y: Int) {
  private def isInBounds(i: Int) = i >= 0 && i <= 7

  def apply(increment: (Int, Int) => (Int, Int)) = PiecePosition(increment(X, Y))

  def isRookDefaultPosition: Boolean = Y == 0 && (X == 0 || X == 7)

  require(isInBounds(X) && isInBounds(Y))
}

object PiecePosition {
  def apply(position: (Int, Int)): PiecePosition = PiecePosition(position._1, position._2)

  def apply(position: String): Option[PiecePosition] = {
    Try {
      position(1).asDigit
    } match {
      case Success(digit) => BoardUtils.letterMapping.get(position(0)).map(x => PiecePosition(x, digit))
      case Failure(_) => None
    }

  }
}
