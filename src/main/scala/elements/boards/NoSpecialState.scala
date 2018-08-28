package elements.boards

import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class NoSpecialState() extends BoardState {
  override def players: Players = ???

  override def kingsPositions: KingsPositions = ???

  override def pieces: List[List[Piece]] = ???

  override def next: BoardState = ???

  override def isPlayerKingUncovered: Boolean = ???
}
