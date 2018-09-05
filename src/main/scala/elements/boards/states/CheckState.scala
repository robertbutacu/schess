package elements.boards.states

import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.{King, Piece}

case class CheckState(pieces: List[List[Piece]],
                      players: Players) extends BoardState {
  override def next: Option[BoardState] = {
    val nextMove = players.getPlayerTurn.askForMove

    val pieceToBeMoved = pieces(nextMove.from.X)(nextMove.from.Y)

    if (pieceToBeMoved.owner.contains(players.getPlayerTurn) && (pieceToBeMoved match {
      case _: King => true
      case _       => false
    })) {
      if (wouldPlayerKingBeInCheck(nextMove)) None
      else None
    } else None
  }
}
