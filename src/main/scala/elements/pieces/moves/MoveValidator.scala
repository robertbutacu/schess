package elements.pieces.moves

import actions.PiecePosition
import elements.boards.{Board, BoardState}
import elements.pieces._

trait MoveValidator[P] {
  def isValidMove(piece: P, to: PiecePosition): Boolean
}

object MoveValidator {

  implicit class BishopValidator(board: BoardState) extends MoveValidator[Bishop] {
    override def isValidMove(piece: Bishop, to: PiecePosition): Boolean =
      Board.isDiagonalMove(piece.position, to)
  }

  implicit class RookValidator(board: BoardState) extends MoveValidator[Rook] {
    override def isValidMove(piece: Rook, to: PiecePosition): Boolean =
      Board.isStraightMove(piece.position, to)
  }

  implicit class KingValidator(board: BoardState) extends MoveValidator[King] {
    override def isValidMove(piece: King, to: PiecePosition): Boolean = {
      val horizontalDif = to.X - piece.position.X
      val verticalDif = to.Y - piece.position.Y

      isAmongAllMoves(verticalDif, horizontalDif, -1, 1, -1, 1)
    }
  }

  implicit class QueenValidator(board: BoardState) extends MoveValidator[Queen] {
    override def isValidMove(piece: Queen, to: PiecePosition): Boolean =
      Board.isStraightMove(piece.position, to) || Board.isDiagonalMove(piece.position, to)
  }

  implicit class KnightValidator(board: BoardState) extends MoveValidator[Knight] {
    override def isValidMove(piece: Knight, to: PiecePosition): Boolean = {
      val verticalDif = to.X - piece.position.X
      val horizontalDif = to.Y - piece.position.Y

      isAmongAllMoves(verticalDif, horizontalDif, -2, +2, -1, +1)
    }
  }

  implicit class PawnValidator(board: BoardState) extends MoveValidator[Pawn] {
    override def isValidMove(piece: Pawn,to: PiecePosition): Boolean = {
      val verticalMove = to.X - piece.position.X
      val horizontalMove = to.Y - piece.position.Y

      verticalMove == 1 && (horizontalMove >= -1 && horizontalMove <= 1)
    }
  }

  private def isAmongAllMoves(verticalDif: Int, horizontalDif: Int,
                              verticalLow: Int, verticalHigh: Int,
                              horizontalLow: Int, horizontalHigh: Int): Boolean =
    verticalDif <= verticalHigh && verticalDif >= verticalLow && horizontalDif >= horizontalLow && horizontalDif <= horizontalHigh

}
