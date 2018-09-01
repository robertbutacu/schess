package elements.boards.states

import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class CheckmateState(pieces: List[List[Piece]], kingsPositions: KingsPositions) extends BoardState {
  override def players: Players = ???

  override def next: Option[BoardState] = {
    println(s"""${players.getPlayerTurn} has won! Sorry, ${players.getOtherPlayerTurn}.""")
    None
  }

  override def isPlayerKingUncovered: Boolean = {
    ???
  }
}
