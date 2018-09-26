package actions

import actions.validators.board.BoardQueries
import elements.boards.states.BoardState
import validator.Validator

import scala.util.{Failure, Success, Try}

case class Position(X: Int, Y: Int) {
  def reverse: Position = this.copy(Y = 7 - this.Y)

  require(isInBounds(X) && isInBounds(Y))

  private def isInBounds(i: Int) = i >= 0 && i <= 7

  def apply(increment: (Int, Int) => (Int, Int)) = Position(increment(X, Y))

  def isRookDefaultPosition(board: BoardState): Validator =
    Validator.toValidate(Y == 0 && (X == 0 || X == 7), "Rook in not default position", board)
}

object Position {
  def apply(position: (Int, Int)): Position = Position(position._1, position._2)

  def apply(position: String): Option[Position] = {
    Try {
      position(1).asDigit
    } match {
      case Success(digit) => BoardQueries.letterMapping.get(position(0)).map(y => Position(digit, y))
      case Failure(_)     => None
    }
  }
}
