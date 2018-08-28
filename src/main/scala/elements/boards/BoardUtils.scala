package elements.boards

import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

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

  def nextPlayer(players: Players) = players.copy(playerTurn = 3 - players.playerTurn)

  def nextPlayerIndex(players: Players) = 3 - players.playerTurn

  def nextPlayerIndex(playerTurn: Int): Int = 3 - playerTurn

}
