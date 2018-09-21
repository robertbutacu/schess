package elements.boards.states

import elements.pieces.Piece
import players.Players

case class CheckmateState(pieces: List[List[Piece]],
                          players: Players) extends BoardState {
  override def next: Option[BoardState] = {
    println(s"""${players.getPlayerTurn} has won! Sorry, ${players.getOtherPlayerTurn}.""")
    None
  }
}
