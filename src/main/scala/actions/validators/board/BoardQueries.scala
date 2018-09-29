package actions.validators.board

import actions.Position
import actions.validators.moves.MoveValidator._
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import config.Config
import elements.boards.states.BoardState
import elements.pieces.{EmptyPosition, King, Pawn, Piece}
import players.models.Player
import validator.ValidatorConverterImplicits.toBoolean
import validator.{Failure, Success, Validator}

object BoardQueries {
  val letterMapping = Map('A' -> 0, 'B' -> 1, 'C' -> 2, 'D' -> 3, 'E' -> 4, 'F' -> 5, 'G' -> 6, 'H' -> 7)
  val numberMapping = Map(0 -> 'A', 1 -> 'B', 2 -> 'C', 3 -> 'D', 4 -> 'E', 5 -> 'F', 6 -> 'G', 7 -> 'H')

  implicit class BoardQueriesImplicit(board: BoardState) {
    def isEnPassantMove(piece: Pawn, to: Position): Boolean = {
      def checkForLeftEnPassant: Boolean = {
        false
      }

      def checkForRightEnPassant: Boolean = false

      def couldBeLeftEnPassant: Boolean = to.X - piece.position.X == -1 && to.Y - piece.position.Y == 1

      if (couldBeLeftEnPassant) checkForLeftEnPassant
      else checkForRightEnPassant
    }

    def isStraightMove(from: Position, to: Position): Validator =
      Validator.toValidate(from.X == to.X || from.Y == to.Y, "Is not straight move!", board)

    def isDiagonalMove(from: Position, to: Position): Validator =
      Validator.toValidate(Math.abs(from.X - to.X) == Math.abs(from.Y - to.Y), "Is not diagonal move!", board)

    def isStall(board: BoardState, playerToPlay: Player): Boolean = ???

    def isEndGame(playerToPlay: Player): Boolean = ???

    def isKingInCheck: Validator = {
      def filterOppositePlayerPieces(): List[Piece] = {
        val otherPlayerTurn = board.players.getPlayerTurn.index.otherPlayerTurn

        for {
          row   <- board.pieces
          piece <- row
          owner <- piece.owner
          if owner.index == otherPlayerTurn
        } yield piece
      }

      val playerKing = board.players.getPlayerTurn.index.getKingPosition(board.kingsPositions)

      val possibleDangers = filterOppositePlayerPieces()

      Validator.toValidate(possibleDangers exists {p => board.isValidMove(p, playerKing)},
        Config.kingNotInCheckMessage,
        board)
    }

    def isKingNotInCheck: Validator = Validator.toValidate(!isKingInCheck, Config.kingInCheckMessage, board)

    private def isEmptyPosition(board: BoardState, X: Int, Y: Int) = board.getPiece(X, Y) match {
      case _: EmptyPosition => true
      case _                => false
    }

    def isCastling(piece: King, to: Position): Validator = {
      //println(piece.isDefaultPosition(board))
      piece.isDefaultPosition(board) andThen to.isRookDefaultPosition(board) andThen board.isOwningRook(to, piece.player)

    }

    def isNotOwnPiece(to: Position, player: Player): Validator =
      if(board.getPiece(to.X, to.Y).owner.contains(player))
        Failure(Config.endPositionOwnPieceMessage, board)
      else Success(board)

    def isNotKing(to: Position): Validator = {
      board.getPiece(to.X, to.Y) match {
        case king: King => Failure(Config.kingPositionMessage, board)
        case _          => Success(board)
      }
    }

    def isClearPath(from: Position, to: Position,
                    incrementFunction: (Int, Int) => (Int, Int)): Validator = {
      def verify(currentPosition: Position): Validator = {
        val next = currentPosition(incrementFunction)

        if (next == to) Success(board)
        else board.isPositionFree(next) andThen verify(next)
      }

      verify(from)
    }
  }
}
