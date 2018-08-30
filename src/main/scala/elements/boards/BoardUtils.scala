package elements.boards

import elements.boards.information.KingsPositions
import elements.pieces.{Empty, Piece}

object BoardUtils {
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
