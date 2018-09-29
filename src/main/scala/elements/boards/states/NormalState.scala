package elements.boards.states

import actions.execute.BoardCategorisation
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import config.Config
import elements.pieces.Piece
import players.Players
import validator.Validator

case class NormalState(initialPieces: List[List[Piece]],
                       players: Players,
                       isFirstMove: Boolean = false) extends BoardState {
  val pieces: List[List[Piece]] =
    if(isFirstMove) initialPieces
    else BoardCategorisation.invertBoard(initialPieces)

  override def next: Option[BoardState] = {
    val nextMove = players.getPlayerTurn.askForMove

    val pieceToBeMoved = getPiece(nextMove.from.X, nextMove.from.Y)
    val doesPlayerOwnPiece = pieceToBeMoved.owner.contains(players.getPlayerTurn)

    def isValidMove: Validator =
      this.isValidMove(pieceToBeMoved, nextMove.to) andThen
        Validator.toValidate(doesPlayerOwnPiece, Config.notOwnPieceMessage, this) andThen
        this.isKingNotInCheck

    Some(isValidMove.next(nextMove))
  }
}
