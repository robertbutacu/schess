package elements.pieces.moves

import actions.Position
import elements.boards.BoardState
import elements.boards.Board._
import elements.pieces._
import elements.pieces.moves.BoardCheckers._

trait MoveValidator[P] {
  def isValidMove(board: BoardState, piece: P, to: Position): Boolean =
    isValidPath(board, piece, to) && canOccupyPosition(board, piece, to)

  protected def isValidPath(board: BoardState, piece: P, to: Position): Boolean

  // needed for en passant and shielding with rook
  protected def canOccupyPosition(board: BoardState, piece: P, to: Position): Boolean
}

object MoveValidator {

  implicit class BoardValidator[P](board: BoardState) {
    def isValidMove(piece: P, to: Position)(implicit validator: MoveValidator[P]): Boolean = {
      validator.isValidMove(board, piece, to)
    }
  }

  implicit val bishopValidator = new MoveValidator[Bishop] {
    override protected def isValidPath(board: BoardState, piece: Bishop, to: Position): Boolean =
      isDiagonalMove(piece.position, to) && board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(board: BoardState, piece: Bishop, to: Position): Boolean =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit val rookValidator = new MoveValidator[Rook] {
    override protected def isValidPath(board: BoardState, piece: Rook, to: Position): Boolean =
      isStraightMove(piece.position, to) && board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(board: BoardState, piece: Rook, to: Position): Boolean =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit val kingValidator = new MoveValidator[King] {
    override protected def isValidPath(board: BoardState, piece: King, to: Position): Boolean = {
      val horizontalMove = List(-1, 0, 1)
      val verticalMove = List(-1, 0, 1)

      isAmongAllMoves(piece, to, verticalMove, horizontalMove) || board.isCastling(piece, to)
    }

    override protected def canOccupyPosition(board: BoardState, piece: King, to: Position): Boolean = {
      board.isCastling(piece, to) || board.isNotOwnPiece(to, piece.player)
    }
  }

  implicit val queenValidator = new MoveValidator[Queen] {
    override protected def isValidPath(board: BoardState, piece: Queen, to: Position): Boolean =
      (isStraightMove(piece.position, to) || isDiagonalMove(piece.position, to)) &&
        board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(board: BoardState, piece: Queen, to: Position): Boolean =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit val knightValidator = new MoveValidator[Knight] {
    override protected def isValidPath(board: BoardState, piece: Knight, to: Position): Boolean = {
      val horizontalMove = List(-1, +1)
      val verticalMove = List(+2, -2)

      isAmongAllMoves(piece, to, verticalMove, horizontalMove)
    }

    override protected def canOccupyPosition(board: BoardState, piece: Knight, to: Position): Boolean =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit val pawnValidator = new MoveValidator[Pawn] {
    override protected def isValidPath(board: BoardState, piece: Pawn, to: Position): Boolean = {
      def isFirstPawnMove = piece.position.Y == 1 && to.Y == 3

      val horizontalMove = to.X - piece.position.X
      val verticalMove = to.Y - piece.position.Y

      // checking vertical move                   checking horizontal move
      (isFirstPawnMove || verticalMove == 1) && (horizontalMove == 0 || board.isEnPassantMove(piece, to))
    }

    override protected def canOccupyPosition(board: BoardState, piece: Pawn, to: Position): Boolean = {
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