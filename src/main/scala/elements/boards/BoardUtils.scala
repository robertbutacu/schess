package elements.boards

import elements.boards.information.KingsPositions
import elements.pieces._
import players.PlayerIndex
import elements.pieces.moves.MoveValidator._

object BoardUtils {
  val letterMapping = Map('A' -> 0, 'B' -> 1, 'C' -> 2, 'D' -> 3, 'E' -> 4, 'F' -> 5, 'G' -> 6, 'H' -> 7)
  val numberMapping = Map(0 -> 'A', 1 -> 'B', 2 -> 'C', 3 -> 'D', 4 -> 'E', 5 -> 'F', 6 -> 'G', 7 -> 'H')

  def isKingInCheckState(board: BoardState, playerTurn: PlayerIndex, kingsPositions: KingsPositions): Boolean = {
    def filterOppositePlayerPieces(): List[Piece] =
      for {
        row   <- board.pieces
        piece <- row
        owner <- piece.owner
        if owner.index == playerTurn.otherPlayerTurn
      } yield piece

    val playerKing = playerTurn.getKingPosition(kingsPositions)

    val possibleDangers = filterOppositePlayerPieces()

    possibleDangers.exists {
      case r: Rook => board.isValidMove(r, playerKing)
      case k: King => board.isValidMove(k, playerKing)
      case q: Queen => board.isValidMove(q, playerKing)
      case kn: Knight => board.isValidMove(kn, playerKing)
      case p: Pawn => board.isValidMove(p, playerKing)
      case b: Bishop => board.isValidMove(b, playerKing)
    }
  }

  def nextPlayerIndex(playerTurn: Int): Int = 3 - playerTurn

  private def isEmptyPosition(board: BoardState, X: Int, Y: Int) = board.pieces(Y)(X) match {
    case _: EmptyPosition => true
    case _        => false
  }

}
