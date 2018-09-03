package actions.execute

import actions.Position
import elements.boards.BoardState
import elements.pieces.{King, Pawn}
import elements.pieces.moves.MoveValidator

object MoveCategorisation {
  def categorise(board: BoardState, from: Position, to: Position): BoardState = {
    board.pieces(from.Y)(from.X) match {
      case king: King =>
        if (MoveValidator.isCastling(board, king, to)) KingCastleMove(board, from, to).go()
        else NormalMove(board, from, to).go()
      case pawn: Pawn =>
        if (MoveValidator.isEnPassantMove(board, pawn, to)) EnPassantMove(board, from, to).go()
        else NormalMove(board, from, to).go()
      case _          => NormalMove(board, from, to).go()
    }
  }
}
