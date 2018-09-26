package elements.boards.states

import actions.execute.BoardCategorisation
import elements.pieces.Piece
import players.Players

case class CheckmateState(initialPieces: List[List[Piece]],
                          players: Players) extends BoardState {
  val pieces: List[List[Piece]] = BoardCategorisation.invertBoard(initialPieces)

  override def next: Option[BoardState] = {
    println(s"""${players.getPlayerTurn} has won! Sorry, ${players.getOtherPlayerTurn}.""")
    None
  }
}
