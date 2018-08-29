package elements.boards

import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class CheckState() extends BoardState {
  override def players: Players = ???

  override def kingsPositions: KingsPositions = ???

  override def next: Option[BoardState] = ???

  override def pieces: List[List[Piece]] = ???

  override def isPlayerKingUncovered: Boolean = ???
}
