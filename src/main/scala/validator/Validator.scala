package validator

import elements.boards.states.BoardState

trait Validator {
  def message: Option[String]

  def board: BoardState

  def next: BoardState

  def andThen(f: => Validator): Validator
}
