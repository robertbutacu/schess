package elements.pieces.moves

import actions.Position
import elements.boards.BoardState
import elements.pieces.King
import elements.pieces.moves.MoveValidator.ops.BoardMoveValidator
import elements.boards.validators.BoardValidators.BoardValidator


object BoardCheckers {

  implicit class Board(board: BoardState) {
    def getPossiblePositionForKing(piece: King, to: Position): Position = {
      def adjustCastlingMoves: Position =
        if (to.X == 0) // left castling
          Position(2, 0)
        else //right castling
          Position(6, 0)

      if(board.isValidMove(piece, to) && board.isCastling(piece, to)) adjustCastlingMoves
      else to
    }
  }
}
