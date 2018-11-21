package validator

import actions.Move
import actions.execute.MoveCategorisation
import game.elements.boards.states.BoardState
import game.elements.pieces.Piece
import game.players.Players

trait Validator extends MoveCategorisation {
  def board: BoardState

  def next(move: Move): BoardState

  def andThen(f: => Validator): Validator

  def orElse(f: => Validator): Validator

  def toBoolean: Boolean
}

object Validator {
  def toValidate(f: => Boolean,
                 failureMessage: String,
                 boardState: BoardState): Validator =
    if(f) Success(boardState)
    else  Failure(failureMessage, boardState)

  def toValidate(list: List[Validator], board: BoardState): Validator = {
    val failures = list.collect{ case failure: Failure => failure}.foldLeft(""){ case (acc, curr) =>
    acc + curr.message}

    if(failures.isEmpty) Success(board)
    else                 Failure(failures, board)
  }

  def toBoardState(f: => Boolean,
                   pieces: List[List[Piece]],
                   players: Players,
                   board: (List[List[Piece]], Players) => BoardState): Option[BoardState] = {
    if(f) Some(board(pieces, players))
    else  None
  }
}
