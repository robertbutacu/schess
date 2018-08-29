package elements.boards

import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class StalemateState() extends BoardState {
  override def players: Players = ???

  override def pieces: List[List[Piece]] = ???

  override def kingsPositions: KingsPositions = ???

  override def next: Option[BoardState] = {
    println(s"""It's a stalemate! Nobody won.""")
    None
  }


  override def isPlayerKingUncovered: Boolean = ???
}