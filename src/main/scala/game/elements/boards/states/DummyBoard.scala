package game.elements.boards.states

import game.elements.pieces.Piece
import game.players.Players

case class DummyBoard(pieces: List[List[Piece]], players: Players) extends BoardState {
  override def next: Option[BoardState] = None
}
