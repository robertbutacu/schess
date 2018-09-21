package elements.boards.states

import elements.pieces.Piece
import players.Players

case class StalemateState(pieces: List[List[Piece]],
                          players: Players) extends BoardState {
  override def next: Option[BoardState] = {
    println(s"""It's a stalemate! Nobody won.""")
    None
  }
}
