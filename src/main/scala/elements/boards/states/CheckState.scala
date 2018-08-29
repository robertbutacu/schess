package elements.boards.states

import elements.boards.BoardState
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.{King, Piece}

case class CheckState() extends BoardState {
  override def players: Players = ???

  override def kingsPositions: KingsPositions = ???

  override def next: Option[BoardState] = {
    val nextMove = players.getPlayerTurn.askForMove

    val pieceToBeMoved = pieces(nextMove.from.X)(nextMove.from.Y)

    if (pieceToBeMoved.owner.contains(players.getPlayerTurn) && (pieceToBeMoved match {
      case _: King => true;
      case _ => false
    })) {
      if (isPlayerKingUncovered) None
      else None
    } else None
  }

  override def pieces: List[List[Piece]] = ???

  override def isPlayerKingUncovered: Boolean = ???
}
