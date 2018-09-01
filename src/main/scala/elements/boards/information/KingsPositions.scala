package elements.boards.information

import actions.PiecePosition
import elements.pieces.King
import players.{PlayerOne, PlayerTwo}

case class KingsPositions(firstKingPosition: PiecePosition, secondKingPosition: PiecePosition) {
  def apply(kingPosition: King) = {
    kingPosition.player.index match {
      case PlayerOne => this.copy(firstKingPosition = kingPosition.position)
      case PlayerTwo => this.copy(secondKingPosition = kingPosition.position)
    }
  }
}