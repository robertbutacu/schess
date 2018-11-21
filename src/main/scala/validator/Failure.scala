package validator
import actions.Move
import game.elements.boards.states.BoardState

case class Failure(message: String, board: BoardState) extends Validator {
  override def next(move: Move): BoardState = {
    println(message)

    board
  }

  override def orElse(f: => Validator): Validator = f

  override def andThen(f: => Validator): Validator = this

  override def toBoolean: Boolean = false
}
