package validator

import actions.execute.MoveCategorisation
import actions.execute.executers.{EnPassantMove, KingCastleMove, NormalMove}
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import actions.{Move, Position}
import elements.boards.states.BoardState
import elements.pieces.{King, Pawn}

case class Success(board: BoardState) extends Validator {
  override def next(move: Move): BoardState = {
    val possibleBoardUpdated = MoveCategorisation.categorise(board, move.from, move.to)

    possibleBoardUpdated
  }

  override def andThen(f: => Validator): Validator = f

  override def orElse(f: => Validator): Validator = this

  override def toBoolean: Boolean = true
}
