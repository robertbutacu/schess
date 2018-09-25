package elements.boards.states

import actions.execute.MoveCategorisation
import elements.pieces.Piece
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import players.Players

case class NormalState(pieces: List[List[Piece]], players: Players) extends BoardState {
  override def next: Option[BoardState] = {
    val nextMove = players.getPlayerTurn.askForMove

    val pieceToBeMoved = getPiece(nextMove.from.X, nextMove.from.Y)

    def isValidMove: Boolean = {
      println("*** player turn " + pieceToBeMoved.owner.contains(players.getPlayerTurn))
      println("*** is valid move " + this.isValidMove(pieceToBeMoved, nextMove.to))
      println("*** piece to be moved" + pieceToBeMoved.owner)
      println("*** piece to be moved" + pieceToBeMoved.position)
      pieceToBeMoved.owner.contains(players.getPlayerTurn) && this.isValidMove(pieceToBeMoved, nextMove.to)
    }

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
