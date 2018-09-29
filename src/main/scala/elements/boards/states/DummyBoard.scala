package elements.boards.states

import elements.pieces.Piece
import players.Players

case class DummyBoard(pieces: List[List[Piece]], players: Players) extends BoardState {
  override def next: Option[BoardState] = None
}
