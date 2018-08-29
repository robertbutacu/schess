package elements.boards.states

import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class NoSpecialState(pieces: List[List[Piece]]) extends BoardState {
  override def players: Players = ???

  override def kingsPositions: KingsPositions = ???

  override def next: Option[BoardState] = ???

  override def isPlayerKingUncovered: Boolean = ???
}
