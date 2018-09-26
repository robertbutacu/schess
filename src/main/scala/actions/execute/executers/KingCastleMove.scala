package actions.execute.executers

import actions.Position
import elements.boards.states.{BoardState, NormalState}


case class KingCastleMove(board: BoardState, from: Position, to: Position) extends ExecuteMove {
  override def go(): BoardState = {
    def adjustCastlingMoves: (Position, Position, Position) =
      if (to.X == 0) // left castling
        (Position(2, 0), Position(0, 0), Position(3, 0))
      else //right castling
        (Position(6, 0), Position(7, 0), Position(5, 0))

    val (kingMove, rookFrom, rookTo) = adjustCastlingMoves

    val kingCastledPieces = updatePiece(board.pieces, from, kingMove)
    val rookAdjustedPieces = updatePiece(kingCastledPieces, rookFrom, rookTo)

    NormalState(rookAdjustedPieces, board.players.switchTurns)
  }
}