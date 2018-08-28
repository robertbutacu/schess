package elements.pieces.moves

import actions.PiecePosition
import elements.pieces._

trait MoveValidator[P] {
  def isValidMove(to: PiecePosition): Boolean
}

object MoveValidator {
  implicit class BishopValidator(bishop: Bishop) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean = ???
  }

  implicit class RookValidator(bishop: Rook) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean = ???
  }

  implicit class KingValidator(bishop: King) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean = ???
  }

  implicit class QueenValidator(bishop: Queen) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean = ???
  }

  implicit class KnightValidator(bishop: Knight) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean = ???
  }

  implicit class PawnValidator(bishop: Pawn) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean = ???
  }
}
