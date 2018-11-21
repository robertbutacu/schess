package game.elements.boards.states

import actions.execute.{BoardCategorisation, MoveCategorisation}
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import config.Config
import game.elements.pieces.{King, Piece}
import game.players.Players
import validator.Validator
import validator.ValidatorConverterImplicits.toBoolean

case class CheckState(initialPieces: List[List[Piece]],
                      players: Players) extends BoardState {
  val pieces: List[List[Piece]] = BoardCategorisation.invertBoard(initialPieces)

  override def next: Option[BoardState] = {
    val nextMove = players.getPlayerTurn.askForMove

    val pieceToBeMoved = getPiece(nextMove.from.X, nextMove.from.Y)

    def isMovingKing: Validator =
      Validator.toValidate(pieceToBeMoved.owner.contains(players.getPlayerTurn) && (pieceToBeMoved match {
        case _: King => true
        case _       => false
      }), Config.notAKingMessage, this)

    def isValidMove: Boolean = isMovingKing andThen this.isValidMove(pieceToBeMoved, nextMove.to) andThen this.isKingNotInCheck

    val possibleBoardUpdated = MoveCategorisation.categorise(this, nextMove.from, nextMove.to)



    if (isValidMove) {
      if (this.isCheck) {
        println("\n The king would still be in check! Please choose another one!")
        this.next
      }
      else Some(possibleBoardUpdated)
    } else {
      println("Please choose one of your pieces - keep in mind that you are in check")
      this.next
    }
  }
}
