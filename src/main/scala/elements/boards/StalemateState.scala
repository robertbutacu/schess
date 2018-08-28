package elements.boards

import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class StalemateState() extends BoardState {
  override def players: Players = ???

  override def pieces: List[List[Piece]] = ???

  override def kingsPositions: KingsPositions = ???

  override def next: BoardState = ???

  override def isPlayerKingUncovered: Boolean = ???
}