package elements.pieces.moves

import actions.PiecePosition
import elements.boards.BoardState
import elements.boards.Board._
import elements.pieces._

trait MoveValidator[P] {
  def isValidMove(piece: P, to: PiecePosition): Boolean = isValidPath(piece, to) && canOccupyPosition(piece, to)
  protected def isValidPath(piece: P, to: PiecePosition): Boolean
  protected def canOccupyPosition(piece: P, to: PiecePosition): Boolean // needed for en passant and shielding with rook
}

object MoveValidator {

  implicit class BishopValidator(board: BoardState) extends MoveValidator[Bishop] {
    override protected def isValidPath(piece: Bishop, to: PiecePosition): Boolean =
      isDiagonalMove(piece.position, to) && isClearPath(board, piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(piece: Bishop, to: PiecePosition): Boolean = ???
  }

  implicit class RookValidator(board: BoardState) extends MoveValidator[Rook] {
    override protected def isValidPath(piece: Rook, to: PiecePosition): Boolean =
      isStraightMove(piece.position, to) && isClearPath(board, piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(piece: Rook, to: PiecePosition): Boolean = ???
  }

  implicit class KingValidator(board: BoardState) extends MoveValidator[King] {
    override protected def isValidPath(piece: King, to: PiecePosition): Boolean = {
      val horizontalMove = List(-1, 0, 1)
      val verticalMove = List(-1, 0, 1)

      isAmongAllMoves(piece, to, verticalMove, horizontalMove)
    }

    override protected def canOccupyPosition(piece: King, to: PiecePosition): Boolean = ???
  }

  implicit class QueenValidator(board: BoardState) extends MoveValidator[Queen] {
    override protected def isValidPath(piece: Queen, to: PiecePosition): Boolean =
      (isStraightMove(piece.position, to) || isDiagonalMove(piece.position, to)) &&
        isClearPath(board, piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(piece: Queen, to: PiecePosition): Boolean = ???
  }

  implicit class KnightValidator(board: BoardState) extends MoveValidator[Knight] {
    override protected def isValidPath(piece: Knight, to: PiecePosition): Boolean = {
      val horizontalMove = List(-1, +1)
      val verticalMove = List(+2, -2)

      isAmongAllMoves(piece, to, verticalMove, horizontalMove)
    }

    override protected def canOccupyPosition(piece: Knight, to: PiecePosition): Boolean = ???
  }

  implicit class PawnValidator(board: BoardState) extends MoveValidator[Pawn] {
    override protected def isValidPath(piece: Pawn, to: PiecePosition): Boolean = {
      val horizontalMove = to.X - piece.position.X
      val verticalMove = to.Y - piece.position.Y

      verticalMove == 1 && (horizontalMove >= -1 && horizontalMove <= 1)
    }

    override protected def canOccupyPosition(piece: Pawn, to: PiecePosition): Boolean = ???
  }

  def isClearPath(board: BoardState,
                  from: PiecePosition, to: PiecePosition,
                  incrementFunction: (Int, Int) => (Int, Int)): Boolean = {
    def verify(currentPosition: PiecePosition): Boolean = {
      val next = currentPosition(incrementFunction)

      if (next == to) true
      else board.isPositionFree(next) && verify(next)
    }

    verify(from)
  }

  private def isAmongAllMoves(piece: Piece,
                              to: PiecePosition,
                              verticalMove: List[Int], horizontalMove: List[Int]): Boolean = {
    horizontalMove.exists { x =>
      verticalMove.exists { y =>
        piece.position.X + x == to.X && piece.position.Y + y == to.Y
      }
    }
  }
}