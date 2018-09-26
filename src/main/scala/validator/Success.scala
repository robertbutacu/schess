package validator

import elements.boards.states.BoardState

case class Success(message: Option[String] = None, board: BoardState) extends Validator {
  override def next: BoardState = ???
}
