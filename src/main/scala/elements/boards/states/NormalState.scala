package elements.boards.states

import actions.execute.MoveCategorisation
import elements.boards.BoardState
import elements.boards.information.Players
import elements.pieces.Piece
import elements.pieces.moves.MoveValidator.ops.BoardMoveValidator
import elements.boards.validators.BoardQueries.BoardQueries

case class NormalState(pieces: List[List[Piece]], players: Players) extends BoardState {
  override def next: Option[BoardState] = {
    val nextMove = players.getPlayerTurn.askForMove

    val pieceToBeMoved = pieces(nextMove.from.X)(nextMove.from.Y)

    def isValidMove: Boolean =
      pieceToBeMoved.owner.contains(players.getPlayerTurn) && this.isValidMove(pieceToBeMoved, nextMove.to)

    val possibleBoardUpdated = MoveCategorisation.categorise(this, nextMove.from, nextMove.to)

    if (isValidMove) {
      if (this.isKingInCheck) {
        println("\n The king would be in check! Please choose another move!")
        this.next
      }
      else Some(possibleBoardUpdated)
    } else {
      println("Please choose one of your pieces!")
      this.next
    }
  }
}
