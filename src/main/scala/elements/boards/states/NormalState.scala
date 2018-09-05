package elements.boards.states

import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class NormalState(pieces: List[List[Piece]], players: Players) extends BoardState {
  override def next: Option[BoardState] = ???
}
