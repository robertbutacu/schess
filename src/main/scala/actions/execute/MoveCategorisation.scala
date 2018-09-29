package actions.execute

import actions.Position
import actions.execute.executers.{EnPassantMove, KingCastleMove, NormalMove}
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import elements.boards.states.BoardState
import elements.pieces.{King, Pawn}
import validator.ValidatorConverterImplicits.toBoolean

object MoveCategorisation {
  def categorise(board: BoardState, from: Position, to: Position): BoardState = {
    board.getPiece(from.X,from.Y) match {
      case king: King =>
        val result = board.isCastling(king, to)
        if (board.isCastling(king, to)) KingCastleMove(board, from, to).go()
        else NormalMove(board, from, to).go()
      case pawn: Pawn =>
        if (board.isEnPassantMove(pawn, to)) EnPassantMove(board, from, to).go()
        else NormalMove(board, from, to).go()
      case _          => NormalMove(board, from, to).go()
    }
  }
}
