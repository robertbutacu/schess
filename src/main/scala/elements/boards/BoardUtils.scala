package elements.boards

import elements.boards.information.KingsPositions
import elements.pieces.{Empty, Piece}

object BoardUtils {
  val letterMapping = Map('A' -> 0, 'B' -> 1, 'C' -> 2, 'D' -> 3, 'E' -> 4, 'F' -> 5, 'G' -> 6, 'H' -> 7)
  val numberMapping = Map(0 -> 'A', 1 -> 'B', 2 -> 'C', 3 -> 'D', 4 -> 'E', 5 -> 'F', 6 -> 'G', 7 -> 'H')

  def isKingInCheckState(pieces: List[List[Piece]], playerTurn: Int, kingsPositions: KingsPositions): Boolean = {
    require(playerTurn == 1 || playerTurn == 2)

    def filterOppositePlayerPieces(): List[Piece] =
      for {
        row <- pieces
        piece <- row
        owner <- piece.owner
        if owner.index == nextPlayerIndex(playerTurn)
      } yield piece

    val playerKing = kingsPositions.getKingPosition(playerTurn)

    val possibleDangers = filterOppositePlayerPieces()

    possibleDangers.exists(p => Board.isValidMove(p, playerKing))
  }

  def nextPlayerIndex(playerTurn: Int): Int = 3 - playerTurn

  private def isEmptyPosition(board: Board, X: Int, Y: Int) = board.table(X)(Y) match {
    case Empty(_) => true
    case _ => false
  }

}
