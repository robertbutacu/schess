package elements.boards.validators

import actions.Position
import elements.boards.BoardState
import elements.boards.information.KingsPositions
import elements.pieces.{EmptyPosition, Piece}
import players.{Player, PlayerIndex}
import elements.pieces.moves.MoveValidator._
import elements.pieces.moves.MoveValidator.ops.BoardMoveValidator

object BoardValidators {
  val letterMapping = Map('A' -> 0, 'B' -> 1, 'C' -> 2, 'D' -> 3, 'E' -> 4, 'F' -> 5, 'G' -> 6, 'H' -> 7)
  val numberMapping = Map(0 -> 'A', 1 -> 'B', 2 -> 'C', 3 -> 'D', 4 -> 'E', 5 -> 'F', 6 -> 'G', 7 -> 'H')

  implicit class BoardValidator(board: BoardState) {

    def isStraightMove(from: Position, to: Position): Boolean = from.X == to.X || from.Y == to.Y

    def isDiagonalMove(from: Position, to: Position): Boolean =
      Math.abs(from.X - to.X) == Math.abs(from.Y - to.Y)

    def isStall(board: BoardState, playerToPlay: Player): Boolean = ???

    def isEndGame(board: BoardState, playerToPlay: Player): Boolean = ???

    def isCheckState(board: BoardState, playerToPlay: Player): Boolean = ???

    def isKingInCheckState(playerTurn: PlayerIndex, kingsPositions: KingsPositions): Boolean = {
      def filterOppositePlayerPieces(): List[Piece] =
        for {
          row   <- board.pieces
          piece <- row
          owner <- piece.owner
          if owner.index == playerTurn.otherPlayerTurn
        } yield piece

      val playerKing = playerTurn.getKingPosition(kingsPositions)

      val possibleDangers = filterOppositePlayerPieces()

      possibleDangers exists(p => board.isValidMove(p, playerKing))
    }

    def nextPlayerIndex(playerTurn: Int): Int = 3 - playerTurn

    private def isEmptyPosition(board: BoardState, X: Int, Y: Int) = board.pieces(Y)(X) match {
      case _: EmptyPosition => true
      case _                => false
    }

  }
}
