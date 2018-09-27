package validator
import elements.boards.states.BoardState

case class Failure(message: String, board: BoardState) extends Validator {
  override def next: BoardState = {
    println(message)

    board
  }

  override def orElse(f: => Validator): Validator = f

  override def andThen(f: => Validator): Validator = this

  override def toBoolean: Boolean = false
}
