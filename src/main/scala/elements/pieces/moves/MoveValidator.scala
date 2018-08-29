package elements.pieces.moves

import actions.PiecePosition
import elements.boards.Board
import elements.pieces._

trait MoveValidator {
  def isValidMove(to: PiecePosition): Boolean
}

object MoveValidator {

  implicit class BishopValidator(bishop: Bishop) extends MoveValidator {
    override def isValidMove(to: PiecePosition): Boolean =
      Board.isDiagonalMove(bishop.piecePosition, to)
  }

  implicit class RookValidator(rook: Rook) extends MoveValidator {
    override def isValidMove(to: PiecePosition): Boolean =
      Board.isStraightMove(rook.piecePosition, to)
  }

  implicit class KingValidator(king: King) extends MoveValidator {
    override def isValidMove(to: PiecePosition): Boolean = {
      val horizontalDif = to.X - king.piecePosition.X
      val verticalDif = to.Y - king.piecePosition.Y

      isAmongAllMoves(verticalDif, horizontalDif, -1, 1, -1, 1)
    }
  }

  implicit class QueenValidator(queen: Queen) extends MoveValidator {
    override def isValidMove(to: PiecePosition): Boolean =
      Board.isStraightMove(queen.piecePosition, to) || Board.isDiagonalMove(queen.piecePosition, to)
  }

  implicit class KnightValidator(knight: Knight) extends MoveValidator {
    override def isValidMove(to: PiecePosition): Boolean = {
      val verticalDif = to.X - knight.piecePosition.X
      val horizontalDif = to.Y - knight.piecePosition.Y

      isAmongAllMoves(verticalDif, horizontalDif, -2, +2, -1, +1)
    }
  }

  implicit class PawnValidator(pawn: Pawn) extends MoveValidator {
    override def isValidMove(to: PiecePosition): Boolean = {
      val verticalMove = to.X - pawn.piecePosition.X
      val horizontalMove = to.Y - pawn.piecePosition.Y

      verticalMove == 1 && (horizontalMove >= -1 && horizontalMove <= 1)
    }
  }

  private def isAmongAllMoves(verticalDif: Int, horizontalDif: Int,
                              verticalLow: Int, verticalHigh: Int,
                              horizontalLow: Int, horizontalHigh: Int): Boolean =
    verticalDif <= verticalHigh && verticalDif >= verticalLow && horizontalDif >= horizontalLow && horizontalDif <= horizontalHigh

}
