package validator

import actions.Position
import actions.execute.executers.{EnPassantMove, KingCastleMove, NormalMove}
import elements.boards.states.BoardState
import elements.pieces.{King, Pawn}
import actions.validators.board.BoardQueries.BoardQueriesImplicit

case class Success(board: BoardState, message: Option[String] = None) extends Validator {
  override def next: BoardState = ???

  override def andThen(f: => Validator): Validator = f

  override def toBoolean: Boolean = true

  private   def categorise(board: BoardState, from: Position, to: Position): BoardState = {
    board.getPiece(from.X,from.Y) match {
      case king: King =>
        if (board.isCastling(king, to).toBoolean) KingCastleMove(board, from, to).go()
        else NormalMove(board, from, to).go()
      case pawn: Pawn =>
        if (board.isEnPassantMove(pawn, to)) EnPassantMove(board, from, to).go()
        else NormalMove(board, from, to).go()
      case _          => NormalMove(board, from, to).go()
    }
  }
}
