package elements.boards.information

import actions.PiecePosition

case class KingsPositions(firstKingPosition: PiecePosition, secondKingPosition: PiecePosition) {
  def getKingPosition(playerTurn: Int): PiecePosition = {
    require(playerTurn == 1 || playerTurn == 2)

    if (playerTurn == 1) firstKingPosition
    else secondKingPosition
  }

}