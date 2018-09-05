package elements.boards.states

import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

class IntermediateState(val pieces: List[List[Piece]],
                        val players: Players,
                        val kingsPositions: KingsPositions) extends BoardState {
  override def next: Option[BoardState] = ???
}
