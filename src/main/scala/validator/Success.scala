package validator

import actions.Move
import game.elements.boards.states.BoardState

case class Success(board: BoardState) extends Validator {
  override def next(move: Move): BoardState = {
    val possibleBoardUpdated = categorise(board, move.from, move.to)

    possibleBoardUpdated
  }

  override def andThen(f: => Validator): Validator = f

  override def orElse(f: => Validator): Validator = this

  override def toBoolean: Boolean = true
}
