package validator
import elements.boards.states.BoardState

case class Failure(message: Option[String], board: BoardState) extends Validator {
  override def next: BoardState = {
    message.foreach(println)

    board
  }

  override def andThen(f: => Validator): Validator = this
}
