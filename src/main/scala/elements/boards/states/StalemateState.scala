package elements.boards.states

import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

case class StalemateState(pieces: List[List[Piece]],
                          players: Players,
                          kingsPositions: KingsPositions) extends BoardState {
  override def next: Option[BoardState] = {
    println(s"""It's a stalemate! Nobody won.""")
    None
  }
}
