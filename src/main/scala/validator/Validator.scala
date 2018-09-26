package validator

import elements.boards.states.BoardState

trait Validator {
  def message: Option[String]

  def board: BoardState

  def next: BoardState

  def andThen(f: => Validator): Validator
}

object Validator {
  def toValidate(f: => Boolean, failureMessage: String, boardState: BoardState): Validator =
    if(f) Success(boardState)
    else Failure(Some(failureMessage), boardState)
}
