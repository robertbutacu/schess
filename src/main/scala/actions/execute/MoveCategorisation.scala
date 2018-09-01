package actions.execute

import actions.Position
import elements.boards.BoardState
import elements.pieces.{King, Pawn}
import elements.pieces.moves.MoveValidator

object MoveCategorisation {
  def categorise(board: BoardState, from: Position, to: Position): ExecuteMove = {
    board(from.Y)(from.X) match {
      case king: King =>
        if(MoveValidator.isCastling(board, king, to)) KingCastleMove(board, from, to)
        else NormalMove(board, from, to)
      case pawn: Pawn => NormalMove(board, from, to)
      case _          => NormalMove(board, from, to)
    }
  }
}
