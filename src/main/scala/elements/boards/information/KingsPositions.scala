package elements.boards.information

import actions.Position
import elements.pieces.King
import players.{PlayerOne, PlayerTwo}

case class KingsPositions(firstKingPosition: Position, secondKingPosition: Position) {
  def apply(kingPosition: King) = {
    kingPosition.player.index match {
      case PlayerOne => this.copy(firstKingPosition = kingPosition.position)
      case PlayerTwo => this.copy(secondKingPosition = kingPosition.position)
    }
  }
}