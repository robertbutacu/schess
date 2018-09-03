package elements.boards.states

import actions.Move
import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class StalemateState(pieces: List[List[Piece]], kingsPositions: KingsPositions) extends BoardState {
  override def players: Players = ???

  override def next: Option[BoardState] = {
    println(s"""It's a stalemate! Nobody won.""")
    None
  }
  
  override def wouldPlayerKingBeInCheck(givenMove: Move): Boolean = ???
}
