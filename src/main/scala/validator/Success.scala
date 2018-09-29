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

  private   def categorise(board: BoardState, from: Position, to: Position): BoardState = {
    board.getPiece(from.X,from.Y) match {
      case king: King =>
        if (board.isCastling(king, to).toBoolean) KingCastleMove(board, from, to).go()
        else NormalMove(board, from, to).go()
      case pawn: Pawn =>
        if (board.isEnPassantMove(pawn, to)) EnPassantMove(board, from, to).go()
        else NormalMove(board, from, to).go()
      case _          => NormalMove(board, from, to).go()
    }
  }
}
