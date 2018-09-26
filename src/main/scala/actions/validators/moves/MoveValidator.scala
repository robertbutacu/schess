package actions.validators.moves

import actions.Position
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import elements.boards.states.BoardState
import elements.pieces._
import players.models.Player
import validator.{Failure, Success, Validator}

trait MoveValidator[P] {
  def isValidMove(board: BoardState, piece: P, to: Position): Validator =
    isValidPath(board, piece, to) andThen canOccupyPosition(board, piece, to) andThen board.isNotKing(to)

  protected def isValidPath(board: BoardState, piece: P, to: Position): Validator

  // needed for en passant and shielding with rook
  protected def canOccupyPosition(board: BoardState, piece: P, to: Position): Validator
}

object MoveValidator {
  def apply[MV](implicit mv: MoveValidator[MV]): MoveValidator[MV] = mv

  object ops {

    implicit class BoardMoveValidator[P](board: BoardState) {
      def isValidMove(piece: P, to: Position)(implicit validator: MoveValidator[P]): Validator = {
        validator.isValidMove(board, piece, to)
      }

      def isOwningRook(position: Position, owner: Player): Validator =
        board.getPiece(position.X, position.Y) match {
          case p: Rook =>
            if(p.owner.forall(p => p == owner)) Success(board)
            else Failure(Some("The rook is not owned by you!\n"), board)
          case _       => Failure(Some("The piece is not a rook!\n"), board)
        }
    }
  }

  implicit val bishopValidator: MoveValidator[Bishop] = new MoveValidator[Bishop] {
    override protected def isValidPath(board: BoardState, piece: Bishop, to: Position): Validator =
      board.isDiagonalMove(piece.position, to) andThen board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(board: BoardState, piece: Bishop, to: Position): Validator =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit val rookValidator: MoveValidator[Rook] = new MoveValidator[Rook] {
    override protected def isValidPath(board: BoardState, piece: Rook, to: Position): Validator =
      board.isStraightMove(piece.position, to) andThen board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(board: BoardState, piece: Rook, to: Position): Validator =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit val kingValidator: MoveValidator[King] = new MoveValidator[King] {
    override protected def isValidPath(board: BoardState, piece: King, to: Position): Validator = {
      val horizontalMove = List(-1, 0, 1)
      val verticalMove = List(-1, 0, 1)

      isAmongAllMoves(board, piece, to, verticalMove, horizontalMove) andThen board.isCastling(piece, to)
    }

    override protected def canOccupyPosition(board: BoardState, piece: King, to: Position): Validator = {
      board.isCastling(piece, to) andThen board.isNotOwnPiece(to, piece.player)
    }
  }

  implicit val queenValidator: MoveValidator[Queen] = new MoveValidator[Queen] {
    override protected def isValidPath(board: BoardState, piece: Queen, to: Position): Validator =
      (board.isStraightMove(piece.position, to) andThen board.isDiagonalMove(piece.position, to)) andThen
        board.isClearPath(piece.position, to, Moves.moveType(piece.position, to))

    override protected def canOccupyPosition(board: BoardState, piece: Queen, to: Position): Validator =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit val knightValidator: MoveValidator[Knight] = new MoveValidator[Knight] {
    override protected def isValidPath(board: BoardState, piece: Knight, to: Position): Validator = {
      val horizontalMove = List(-1, +1)
      val verticalMove = List(+2, -2)

      isAmongAllMoves(board, piece, to, verticalMove, horizontalMove)
    }

    override protected def canOccupyPosition(board: BoardState, piece: Knight, to: Position): Validator =
      board.isNotOwnPiece(to, piece.player)
  }

  implicit val pawnValidator: MoveValidator[Pawn] = new MoveValidator[Pawn] {
    override protected def isValidPath(board: BoardState, piece: Pawn, to: Position): Validator = {
      def isFirstPawnMove: Validator =
        Validator.toValidate(piece.position.Y == 1 && to.Y == 3, "Is not first pawn move\n", board)

      val horizontalMove = to.X - piece.position.X
      val verticalMove = to.Y - piece.position.Y

      def isEatingPiece: Validator = {
        def isDiagonalMove: Validator =
          Validator.toValidate(Math.abs(horizontalMove) == 1 && verticalMove == 1, "is not diagonal move for pawn\n", board)
        def isNotOwnPiece: Validator =
          Validator.toValidate(board.getPiece(to.X, to.Y).owner != piece.owner, "own piece cannot be eaten\n", board)

        isDiagonalMove orElse isNotOwnPiece
      }


      def isStraightMove: Validator =
      // checking vertical move                   checking horizontal move
        isFirstPawnMove andThen Validator.toValidate(
          (verticalMove == 1) && (horizontalMove == 0 || board.isEnPassantMove(piece, to)),
          "Is not straight move for pawn\n", board)

      isEatingPiece orElse isStraightMove
    }

    override protected def canOccupyPosition(board: BoardState, piece: Pawn, to: Position): Validator = {
      def isEmptySpaceForVerticalMove: Validator = {
        if(piece.position.X == to.X && piece.position.Y + 1 == to.Y) board.isPositionFree(to)
        else Failure(Some("isEmptySpaceForVerticalMove\n"), board)
      }

      isEmptySpaceForVerticalMove orElse board.isNotOwnPiece(to, piece.player)
    }
  }

  implicit val pieceValidator: MoveValidator[Piece] = new MoveValidator[Piece] {
    override protected def isValidPath(board: BoardState, piece: Piece, to: Position): Validator =
      piece match {
        case king: King       => implicitly[MoveValidator[King]].isValidPath(board, king, to)
        case queen: Queen     => implicitly[MoveValidator[Queen]].isValidPath(board, queen, to)
        case knight: Knight   => implicitly[MoveValidator[Knight]].isValidPath(board, knight, to)
        case pawn: Pawn       => implicitly[MoveValidator[Pawn]].isValidPath(board, pawn, to)
        case bishop: Bishop   => implicitly[MoveValidator[Bishop]].isValidPath(board, bishop, to)
        case rook: Rook       => implicitly[MoveValidator[Rook]].isValidPath(board, rook, to)
        case _: EmptyPosition => Failure(Some("The position is empty!\n"), board) // moving an empty piece is never valid
      }

    override protected def canOccupyPosition(board: BoardState, piece: Piece, to: Position): Validator =
      piece match {
        case king: King       => implicitly[MoveValidator[King]].canOccupyPosition(board, king, to)
        case queen: Queen     => implicitly[MoveValidator[Queen]].canOccupyPosition(board, queen, to)
        case knight: Knight   => implicitly[MoveValidator[Knight]].canOccupyPosition(board, knight, to)
        case pawn: Pawn       => implicitly[MoveValidator[Pawn]].canOccupyPosition(board, pawn, to)
        case bishop: Bishop   => implicitly[MoveValidator[Bishop]].canOccupyPosition(board, bishop, to)
        case rook: Rook       => implicitly[MoveValidator[Rook]].canOccupyPosition(board, rook, to)
        case _: EmptyPosition => Failure(Some("The position is empty!\n"), board) // moving an empty piece is never valid
      }
  }

  private def isAmongAllMoves(board: BoardState,
                              piece: Piece,
                              to: Position,
                              verticalMove: List[Int], horizontalMove: List[Int]): Validator = {
    horizontalMove.exists { x =>
      verticalMove.exists { y =>
        piece.position.X + x == to.X && piece.position.Y + y == to.Y
      }
    } match {
      case true => Success(board)
      case false => Failure(Some("isAmongAllMoves\n"), board)
    }
  }
}