package elements.boards.states

import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class NoSpecialState() extends BoardState {
  override def players: Players = ???

  override def kingsPositions: KingsPositions = ???

  override def pieces: List[List[Piece]] = ???

  override def next: Option[BoardState] = ???

  override def isPlayerKingUncovered: Boolean = ???
}
