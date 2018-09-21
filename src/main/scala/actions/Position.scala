package actions

import actions.validators.board.BoardQueries

import scala.util.{Failure, Success, Try}

case class Position(X: Int, Y: Int) {
  require(isInBounds(X) && isInBounds(Y))

  private def isInBounds(i: Int) = i >= 0 && i <= 7

  def apply(increment: (Int, Int) => (Int, Int)) = Position(increment(X, Y))

  def isRookDefaultPosition: Boolean = Y == 0 && (X == 0 || X == 7)
}

object Position {
  def apply(position: (Int, Int)): Position = Position(position._1, position._2)

  def apply(position: String): Option[Position] = {
    Try {
      position(1).asDigit
    } match {
      case Success(digit) => BoardQueries.letterMapping.get(position(0)).map(x => Position(x, digit))
      case Failure(_)     => None
    }
  }
}
