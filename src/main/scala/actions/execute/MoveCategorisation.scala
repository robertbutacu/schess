package actions.execute

import actions.Position
import elements.boards.BoardState
import elements.pieces.{King, Pawn}
import elements.boards.validators.BoardQueries.BoardQueries

object MoveCategorisation {
  def categorise(board: BoardState, from: Position, to: Position): BoardState = {
    board.pieces(from.Y)(from.X) match {
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
