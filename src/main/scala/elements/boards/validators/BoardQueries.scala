package elements.boards.validators

import actions.Position
import elements.boards.BoardState
import elements.pieces.{EmptyPosition, King, Pawn, Piece}
import players.Player
import elements.pieces.moves.MoveValidator._
import elements.pieces.moves.MoveValidator.ops.BoardMoveValidator

object BoardQueries {
  val letterMapping = Map('A' -> 0, 'B' -> 1, 'C' -> 2, 'D' -> 3, 'E' -> 4, 'F' -> 5, 'G' -> 6, 'H' -> 7)
  val numberMapping = Map(0 -> 'A', 1 -> 'B', 2 -> 'C', 3 -> 'D', 4 -> 'E', 5 -> 'F', 6 -> 'G', 7 -> 'H')

  implicit class BoardQueries(board: BoardState) {
    def isEnPassantMove(piece: Pawn, to: Position): Boolean = {
      def checkForLeftEnPassant: Boolean = {
        false
      }

      def checkForRightEnPassant: Boolean = false

      def couldBeLeftEnPassant: Boolean = to.X - piece.position.X == -1 && to.Y - piece.position.Y == 1

      if (couldBeLeftEnPassant) checkForLeftEnPassant
      else checkForRightEnPassant
    }

    def isStraightMove(from: Position, to: Position): Boolean = from.X == to.X || from.Y == to.Y

    def isDiagonalMove(from: Position, to: Position): Boolean =
      Math.abs(from.X - to.X) == Math.abs(from.Y - to.Y)

    def isStall(board: BoardState, playerToPlay: Player): Boolean = ???

    def isEndGame(playerToPlay: Player): Boolean = ???

    def isKingInCheck: Boolean = {
      def filterOppositePlayerPieces(): List[Piece] =
        for {
          row   <- board.pieces
          piece <- row
          owner <- piece.owner
          if owner.index == board.players.getPlayerTurn.index.otherPlayerTurn
        } yield piece

      val playerKing = board.players.getPlayerTurn.index.getKingPosition(board.kingsPositions)

      val possibleDangers = filterOppositePlayerPieces()

      possibleDangers exists (p => board.isValidMove(p, playerKing))
    }

    def isKingNotInCheck: Boolean = !isKingInCheck

    private def isEmptyPosition(board: BoardState, X: Int, Y: Int) = board.pieces(Y)(X) match {
      case _: EmptyPosition => true
      case _                => false
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
