package actions.validators.board

import actions.validators.board.BoardQueries.BoardQueriesImplicit
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import game.elements.boards.states.BoardState
import game.elements.pieces.King
import validator.ValidatorConverterImplicits.toBoolean
import game.elements.boards.Position

object BoardCheckers {
  implicit class Board(board: BoardState) {
    def getPossiblePositionForKing(piece: King, to: Position): Position = {
      def adjustCastlingMoves: Position =
        if (to.X == 0) Position(2, 0) // left castling
        else           Position(6, 0) // right castling

      if(board.isValidMove(piece, to) && board.isCastling(piece, to)) adjustCastlingMoves
      else to
    }
  }
}
