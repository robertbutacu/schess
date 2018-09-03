package elements.pieces.moves

import actions.Position
import elements.boards.BoardState
import elements.pieces.{King, Pawn, Rook}
import players.Player

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

    def isCastling(piece: King, to: Position): Boolean =
      piece.isDefaultPosition && to.isRookDefaultPosition && board.isPieceOwner[Rook](to, piece.player)


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
