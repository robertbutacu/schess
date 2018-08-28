package elements.pieces.moves

import actions.PiecePosition
import elements.boards.Board
import elements.pieces._

trait MoveValidator[P] {
  def isValidMove(to: PiecePosition): Boolean
}

object MoveValidator {

  implicit class BishopValidator(bishop: Bishop) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean =
      Board.isDiagonalMove(bishop.piecePosition, to)
  }

  implicit class RookValidator(rook: Rook) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean =
      Board.isStraightMove(rook.piecePosition, to)
  }

  implicit class KingValidator(king: King) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean = {

      val horizontalMove = List(-1, 0, 1)
      val verticalMove = List(-1, 0, 1)

      isAmongAllMoves(king, verticalMove, horizontalMove, to)
    }
  }

  implicit class QueenValidator(queen: Queen) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean =
      Board.isStraightMove(queen.piecePosition, to) || Board.isDiagonalMove(queen.piecePosition, to)
  }

  implicit class KnightValidator(knight: Knight) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean = {
      val horizontalMove = List(-1, +1)
      val verticalMove = List(+2, -2)

      isAmongAllMoves(knight, verticalMove, horizontalMove, to)
    }
  }

  implicit class PawnValidator(pawn: Pawn) extends MoveValidator[Bishop] {
    override def isValidMove(to: PiecePosition): Boolean = {
      val verticalMove = List(-1, 0, +1)
      val horizontalMove = List(0, +1)

      isAmongAllMoves(pawn, verticalMove, horizontalMove, to)

    }
  }

  private def isAmongAllMoves(piece: Piece, verticalMove: List[Int], horizontalMove: List[Int], to: PiecePosition): Boolean = {
    horizontalMove.exists { x =>
      verticalMove.exists { y =>
        piece.piecePosition.X + x == to.X && piece.piecePosition.Y + y == to.Y
      }
    }
  }
}
