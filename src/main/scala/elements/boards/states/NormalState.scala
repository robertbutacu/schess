package elements.boards.states

import actions.Move
import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class NormalState(pieces: List[List[Piece]]) extends BoardState {
  override def players: Players = ???

  override def kingsPositions: KingsPositions = ???

  override def next: Option[BoardState] = ???

  override def wouldPlayerKingBeInCheck(givenMove: Move): Boolean = ???
}
