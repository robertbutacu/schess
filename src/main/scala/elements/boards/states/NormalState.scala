package elements.boards.states

import actions.execute.{BoardCategorisation, MoveCategorisation}
import elements.pieces.Piece
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import config.Config
import players.Players
import validator.{Success, Validator}
import validator.ValidatorConverterImplicits.toBoolean

case class NormalState(initialPieces: List[List[Piece]],
                       players: Players,
                       isFirstMove: Boolean = false) extends BoardState {
  val pieces: List[List[Piece]] =
    if(isFirstMove) initialPieces
    else BoardCategorisation.invertBoard(initialPieces)

  override def next: Option[BoardState] = {
    val nextMove = players.getPlayerTurn.askForMove

    val pieceToBeMoved = getPiece(nextMove.from.X, nextMove.from.Y)

    def isValidMove: Validator =
      this.isValidMove(pieceToBeMoved, nextMove.to) andThen
        Validator.toValidate(pieceToBeMoved.owner.contains(players.getPlayerTurn), Config.notOwnPiece, this) andThen
        Validator.toValidate(this.isKingInCheck, Config.kingInCheck, this)

    Some(isValidMove.next(nextMove))
  }
}
