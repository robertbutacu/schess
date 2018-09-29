package validator

import actions.Move
import elements.boards.states.BoardState

trait Validator {
  def board: BoardState

  def next(move: Move): BoardState

  def andThen(f: => Validator): Validator

  def orElse(f: => Validator): Validator

  def toBoolean: Boolean
}

object Validator {
  def toValidate(f: => Boolean, failureMessage: String, boardState: BoardState): Validator =
    if(f) Success(boardState)
    else Failure(failureMessage, boardState)

  def toValidate(list: List[Validator], board: BoardState): Validator = {
    val failures = list.collect{ case failure: Failure => failure}.foldLeft(""){ case (acc, curr) =>
    acc + curr.message}

    if(failures.isEmpty) Success(board)
    else Failure(failures, board)
  }
}
