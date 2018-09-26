package validator

import elements.boards.states.BoardState

trait Validator {
  def message: Option[String]

  def board: BoardState

  def next: BoardState
}
