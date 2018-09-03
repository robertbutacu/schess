package elements.boards.information

import actions.Position
import elements.pieces.{King, Piece}
import players.{PlayerOne, PlayerTwo}

case class KingsPositions(firstKingPosition: Position, secondKingPosition: Position) {
  def apply(kingPosition: King) = {
    kingPosition.player.index match {
      case PlayerOne => this.copy(firstKingPosition = kingPosition.position)
      case PlayerTwo => this.copy(secondKingPosition = kingPosition.position)
    }
  }
}

object KingsPositions {
  def apply(pieces: List[List[Piece]]): KingsPositions = {
    val kings = for {
      row <- pieces
      piece <- row
      if (piece match {
        case _: King => true
        case _ => false
      })
    } yield piece

    //there are only 2 possible kings
    val (firstKingFound, secondKingFound) = (kings.head, kings.tail.head)

    if (firstKingFound.owner.exists(p => p.index == PlayerOne))
      KingsPositions(firstKingFound.position, secondKingFound.position)
    else KingsPositions(secondKingFound.position, firstKingFound.position)
  }
}