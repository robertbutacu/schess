package actions.validators.board

import actions.validators.moves.MoveValidator._
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import config.Config
import game.elements.boards.states.BoardState
import game.elements.pieces.{EmptyPosition, King, Pawn, Piece}
import game.players.models.Player
import actions.validators.validator.ValidatorConverterImplicits.toBoolean
import actions.validators.validator.{Failure, Success, Validator}
import game.elements.boards.Position

object BoardQueries {
  val letterMapping = Map('A' -> 0, 'B' -> 1, 'C' -> 2, 'D' -> 3, 'E' -> 4, 'F' -> 5, 'G' -> 6, 'H' -> 7)
  val numberMapping = letterMapping.map(_.swap)

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

    //TODO implement this
    def isStall: Boolean = false

    def isEndGame: Boolean = false

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

      //TODO maybe renaming ?
      //TODO add the cases when an own piece can stop the attack
      Validator.toValidate(possibleDangers exists {p => board.isValidMove(p, playerKing)},
        Config.kingNotInCheckMessage,
        board)
    }

    def isKingNotInCheck: Validator = Validator.toValidate(!isKingInCheck, Config.kingInCheckMessage, board)

    def isCastling(piece: King, to: Position): Validator =
      piece.isDefaultPosition(board) andThen to.isRookDefaultPosition(board) andThen board.isOwningRook(to, piece.player)

    def isNotOwnPiece(to: Position, player: Player): Validator =
      if(board.getPiece(to.X, to.Y).owner.contains(player)) Failure(Config.endPositionOwnPieceMessage, board)
      else                                                  Success(board)

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
