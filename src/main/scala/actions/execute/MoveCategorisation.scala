package actions.execute

import actions.execute.executers.{EnPassantMove, KingCastleMove, NormalMove}
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import game.elements.boards.states.BoardState
import game.elements.pieces.{King, Pawn}
import validator.ValidatorConverterImplicits.toBoolean
import game.elements.boards.Position

object MoveCategorisation {
  def categorise(board: BoardState, from: Position, to: Position): BoardState = {
    board.getPiece(from.X,from.Y) match {
      case king: King =>
        if (board.isCastling(king, to)) KingCastleMove(board, from, to).go()
        else NormalMove(board, from, to).go()

      case pawn: Pawn =>
        if (board.isEnPassantMove(pawn, to)) EnPassantMove(board, from, to).go()
        else NormalMove(board, from, to).go()

      case _          => NormalMove(board, from, to).go()
    }
  }
}
