package elements.boards.states

import elements.boards.BoardState
import elements.boards.information.Players
import elements.pieces.Piece

case class CheckmateState(pieces: List[List[Piece]],
                          players: Players) extends BoardState {
  override def next: Option[BoardState] = {
    println(s"""${players.getPlayerTurn} has won! Sorry, ${players.getOtherPlayerTurn}.""")
    None
  }
}
