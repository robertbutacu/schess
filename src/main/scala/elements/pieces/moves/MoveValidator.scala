package elements.pieces.moves

import actions.Position
import elements.boards.BoardState
import elements.boards.Board._
import elements.pieces._
import elements.pieces.moves.BoardCheckers._

trait MoveValidator[P <: Piece] {
  def isValidMove(piece: P, to: Position): Boolean = isValidPath(piece, to) && canOccupyPosition(piece, to)

  protected def isValidPath(piece: P, to: Position): Boolean

  // needed for en passant and shielding with rook
  protected def canOccupyPosition(piece: P, to: Position): Boolean
}

object MoveValidator {

  implicit class BishopValidator(board: BoardState) extends MoveValidator[Bishop] {
    override protected def isValidPath(piece: Bishop, to: Position): Boolean =
      isDiagonalMove(piece.position, to) && board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(piece: Bishop, to: Position): Boolean =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit class RookValidator(board: BoardState) extends MoveValidator[Rook] {
    override protected def isValidPath(piece: Rook, to: Position): Boolean =
      isStraightMove(piece.position, to) && board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(piece: Rook, to: Position): Boolean =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit class KingValidator(board: BoardState) extends MoveValidator[King] {
    override protected def isValidPath(piece: King, to: Position): Boolean = {
      val horizontalMove = List(-1, 0, 1)
      val verticalMove = List(-1, 0, 1)

      isAmongAllMoves(piece, to, verticalMove, horizontalMove) || board.isCastling(piece, to)
    }

    override protected def canOccupyPosition(piece: King, to: Position): Boolean = {
      board.isCastling(piece, to) || board.isNotOwnPiece(to, piece.player)
    }
  }

  implicit class QueenValidator(board: BoardState) extends MoveValidator[Queen] {
    override protected def isValidPath(piece: Queen, to: Position): Boolean =
      (isStraightMove(piece.position, to) || isDiagonalMove(piece.position, to)) &&
        board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(piece: Queen, to: Position): Boolean =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit class KnightValidator(board: BoardState) extends MoveValidator[Knight] {
    override protected def isValidPath(piece: Knight, to: Position): Boolean = {
      val horizontalMove = List(-1, +1)
      val verticalMove = List(+2, -2)

      isAmongAllMoves(piece, to, verticalMove, horizontalMove)
    }

    override protected def canOccupyPosition(piece: Knight, to: Position): Boolean =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit class PawnValidator(board: BoardState) extends MoveValidator[Pawn] {
    override protected def isValidPath(piece: Pawn, to: Position): Boolean = {
      def isFirstPawnMove = piece.position.Y == 1 && to.Y == 3

      val horizontalMove = to.X - piece.position.X
      val verticalMove = to.Y - piece.position.Y

      // checking vertical move                   checking horizontal move
      (isFirstPawnMove || verticalMove == 1) && (horizontalMove == 0 || board.isEnPassantMove(piece, to))
    }

    override protected def canOccupyPosition(piece: Pawn, to: Position): Boolean = {
      board.isNotOwnPiece(to, piece.player)
    }
  }

  private def isAmongAllMoves(piece: Piece,
                              to: Position,
                              verticalMove: List[Int], horizontalMove: List[Int]): Boolean = {
    horizontalMove.exists { x =>
      verticalMove.exists { y =>
        piece.position.X + x == to.X && piece.position.Y + y == to.Y
      }
    }
  }
}