package elements.boards.states

import actions.execute.BoardCategorisation
import elements.pieces.Piece
import players.Players

case class StalemateState(initialPieces: List[List[Piece]],
                          players: Players) extends BoardState {
  val pieces: List[List[Piece]] = BoardCategorisation.invertBoard(initialPieces)

  override def next: Option[BoardState] = {
    println(s"""It's a stalemate! Nobody won.""")
    None
  }
}
