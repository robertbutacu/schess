package actions.validators.board

import actions.Position
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import elements.boards.states.BoardState
import elements.pieces.King
import validator.ValidatorConverterImplicits.toBoolean

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
