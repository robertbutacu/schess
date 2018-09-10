package elements.pieces.moves

import actions.Position
import elements.boards.BoardState
import elements.pieces._
import elements.pieces.moves.BoardCheckers._
import players.Player
import elements.boards.validators.BoardValidators.BoardValidator

trait MoveValidator[P] {
  def isValidMove(board: BoardState, piece: P, to: Position): Boolean =
    isValidPath(board, piece, to) && canOccupyPosition(board, piece, to)

  protected def isValidPath(board: BoardState, piece: P, to: Position): Boolean

  // needed for en passant and shielding with rook
  protected def canOccupyPosition(board: BoardState, piece: P, to: Position): Boolean
}

object MoveValidator {
  def apply[MV](implicit mv: MoveValidator[MV]): MoveValidator[MV] = mv

  object ops {

    implicit class BoardMoveValidator[P](board: BoardState) {
      def isValidMove(piece: P, to: Position)(implicit validator: MoveValidator[P]): Boolean = {
        validator.isValidMove(board, piece, to)
      }

      def isOwningRook(position: Position, owner: Player): Boolean =
        board.pieces(position.Y)(position.X) match {
          case p: Rook => val result = p.owner.forall(p => p == owner)
            result
          case _    => false
        }
    }
  }

  implicit val bishopValidator = new MoveValidator[Bishop] {
    override protected def isValidPath(board: BoardState, piece: Bishop, to: Position): Boolean =
      board.isDiagonalMove(piece.position, to) && board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(board: BoardState, piece: Bishop, to: Position): Boolean =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit val rookValidator = new MoveValidator[Rook] {
    override protected def isValidPath(board: BoardState, piece: Rook, to: Position): Boolean =
      board.isStraightMove(piece.position, to) && board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

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
      (board.isStraightMove(piece.position, to) || board.isDiagonalMove(piece.position, to)) &&
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
      def isEmptySpaceForVerticalMove = {
        if(piece.position.X == to.X && piece.position.Y + 1 == to.Y) board.isPositionFree(to)
        else true
      }

      isEmptySpaceForVerticalMove && board.isNotOwnPiece(to, piece.player)
    }
  }

  implicit val pieceValidator = new MoveValidator[Piece] {
    override protected def isValidPath(board: BoardState, piece: Piece, to: Position): Boolean =
      piece match {
        case king: King       => implicitly[MoveValidator[King]].isValidPath(board, king, to)
        case queen: Queen     => implicitly[MoveValidator[Queen]].isValidPath(board, queen, to)
        case knight: Knight   => implicitly[MoveValidator[Knight]].isValidPath(board, knight, to)
        case pawn: Pawn       => implicitly[MoveValidator[Pawn]].isValidPath(board, pawn, to)
        case bishop: Bishop   => implicitly[MoveValidator[Bishop]].isValidPath(board, bishop, to)
        case rook: Rook       => implicitly[MoveValidator[Rook]].isValidPath(board, rook, to)
        case _: EmptyPosition => false // moving an empty piece is never valid
      }

    override protected def canOccupyPosition(board: BoardState, piece: Piece, to: Position): Boolean =
      piece match {
        case king: King       => implicitly[MoveValidator[King]].canOccupyPosition(board, king, to)
        case queen: Queen     => implicitly[MoveValidator[Queen]].canOccupyPosition(board, queen, to)
        case knight: Knight   => implicitly[MoveValidator[Knight]].canOccupyPosition(board, knight, to)
        case pawn: Pawn       => implicitly[MoveValidator[Pawn]].canOccupyPosition(board, pawn, to)
        case bishop: Bishop   => implicitly[MoveValidator[Bishop]].canOccupyPosition(board, bishop, to)
        case rook: Rook       => implicitly[MoveValidator[Rook]].canOccupyPosition(board, rook, to)
        case _: EmptyPosition => false // moving an empty piece is never valid
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