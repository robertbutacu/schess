package elements.boards.states

import actions.execute.MoveCategorisation
import elements.boards.BoardState
import elements.boards.information.Players
import elements.pieces.{King, Piece}

case class CheckState(pieces: List[List[Piece]],
                      players: Players) extends BoardState {
  override def next: Option[BoardState] = {
    val nextMove = players.getPlayerTurn.askForMove

    val pieceToBeMoved = pieces(nextMove.from.X)(nextMove.from.Y)

    def isValidMove: Boolean = pieceToBeMoved.owner.contains(players.getPlayerTurn) && (pieceToBeMoved match {
      case _: King => true
      case _       => false
    })

    if (isValidMove) {
      if (wouldPlayerKingBeInCheck(nextMove)) {
        println("\n The king would still be in check! Please choose another one!")
        this.next
      }
      else Some(MoveCategorisation.categorise(this, nextMove.from, nextMove.to))
    } else {
      println("Please choose one of your pieces - keep in mind that you are in check")
      this.next
    }
  }
}
