package elements.pieces.moves

import actions.Position
import elements.boards.BoardState
import elements.pieces.{King, Pawn, Piece}
import players.Player
import elements.pieces.moves.MoveValidator.ops.BoardMoveValidator

object BoardCheckers {

  implicit class Board(board: BoardState) {
    def isEnPassantMove(piece: Pawn, to: Position): Boolean = {
      def checkForLeftEnPassant: Boolean = {
        false
      }

      def checkForRightEnPassant: Boolean = false

      def couldBeLeftEnPassant: Boolean = to.X - piece.position.X == -1 && to.Y - piece.position.Y == 1

      if (couldBeLeftEnPassant) checkForLeftEnPassant
      else checkForRightEnPassant
    }

    def getPossiblePositionForKing(piece: King, to: Position): Position = {
      def adjustCastlingMoves: Position =
        if (to.X == 0) // left castling
          Position(2, 0)
        else //right castling
          Position(6, 0)

      if(board.isValidMove(piece, to) && board.isCastling(piece, to)) adjustCastlingMoves
      else to
    }

    def isCastling(piece: King, to: Position): Boolean =
      piece.isDefaultPosition && to.isRookDefaultPosition && board.isOwningRook(to, piece.player)


    def isNotOwnPiece(to: Position, player: Player): Boolean =
      !board.pieces(to.X)(to.Y).owner.contains(player)

    def isClearPath(from: Position, to: Position,
                    incrementFunction: (Int, Int) => (Int, Int)): Boolean = {
      def verify(currentPosition: Position): Boolean = {
        val next = currentPosition(incrementFunction)

        if (next == to) true
        else board.isPositionFree(next) && verify(next)
      }

      verify(from)
    }
  }
}
