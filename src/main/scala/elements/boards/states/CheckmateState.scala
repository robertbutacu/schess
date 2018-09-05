package elements.boards.states

import actions.Move
import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class CheckmateState(pieces: List[List[Piece]],
                          players: Players) extends BoardState {
  override def next: Option[BoardState] = {
    println(s"""${players.getPlayerTurn} has won! Sorry, ${players.getOtherPlayerTurn}.""")
    None
  }

  override def wouldPlayerKingBeInCheck(givenMove: Move): Boolean = {
    ???
  }
}
