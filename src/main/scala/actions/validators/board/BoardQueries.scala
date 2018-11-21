package actions.validators.board

import actions.validators.board.BoardCheckers.Board
import actions.validators.moves.MoveValidator._
import actions.validators.moves.MoveValidator.ops.BoardMoveValidator
import actions.validators.moves.{Moves, PathConstructor}
import config.Config
import game.elements.boards.Position
import game.elements.boards.states.BoardState
import game.elements.pieces._
import game.players.models.Player
import validator.ValidatorConverterImplicits.toBoolean
import validator.{Failure, Success, Validator}

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

    def isStalemate: Boolean = {
      val possibleMoves = for {
        row                 <- board.pieces
        piece               <- row
        if piece.owner.forall(p => p.index.toInt == board.players.playerTurn)
        possibleMoveRow     <- board.pieces
        possibleEndPosition <- possibleMoveRow
        if board.isValidMove(piece, possibleEndPosition.position)
      } yield piece

      possibleMoves.isEmpty
    }

    def isEndGame: Boolean = false

    def isCheckmate: Boolean = {
      val playerTurnIndex = board.players.getPlayerTurn.index

      def isEnemyPlayer(p: Piece) = p.owner.forall(player => player.index != playerTurnIndex)

      def isKingBlocked: Boolean = {
        val playerKing = board.getKingForCurrentPlayer

        val possibleMoves = for {
          row   <- board.pieces
          piece <- row
          if board.isValidMove(playerKing, piece.position)
        } yield board.getPossiblePositionForKing(playerKing, piece.position)

        // there exists a move on the board which would threaten the king in his possible new position
        possibleMoves.forall {
          pm =>
            board.pieces
              .exists { row => row.exists { p => isEnemyPlayer(p) && board.isValidMove(p, pm) } }
        }
      }

      def canKingBeCovered: Boolean = {
        val playerKing = board.getKingForCurrentPlayer

        def isNotKnight(piece: Piece): Boolean = piece match {
          case _: Knight => true
          case _         => false
        }

        val pieces = for {
          row   <- board.pieces
          enemyPiece <- row
          if board.isValidMove(enemyPiece, playerKing.position)
          if isNotKnight(enemyPiece)
          trajectory = PathConstructor.construct(board, enemyPiece, playerKing.position, Moves.moveType(enemyPiece.position, playerKing.position))
          allyPiece  <- row
          if allyPiece.owner.contains(board.players.getPlayerTurn)
          if trajectory.exists{case (x, y) => board.isValidMove(allyPiece, Position(x, y))}
        } yield allyPiece

        pieces.nonEmpty
      }

      if(isCheck) isKingBlocked || canKingBeCovered
      else          areThereOtherMovesAvailable()
    }

    def isKingNotInCheck: Validator = Validator.toValidate(!isCheck, Config.kingInCheckMessage, board)

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


    def isCheck: Boolean = {
      val enemyPlayerIndex = board.players.getPlayerTurn.index
      val kingPosition = board.getKingPositionForCurrentPlayer

      val threateningPieces = for {
        row   <- board.pieces
        piece <- row
        if piece.owner.forall(p => p.index == enemyPlayerIndex)
        if board.isValidMove(piece, kingPosition)
      } yield piece

      threateningPieces.nonEmpty
    }

    def areThereOtherMovesAvailable(): Boolean = {
      val playerTurnIndex = board.players.getPlayerTurn.index

      (for {
        row        <- board.pieces
        piece      <- row
        toRow      <- board.pieces
        otherPiece <- toRow
        if piece != otherPiece
        if piece.owner.forall(player => player.index == playerTurnIndex)
        if board.isValidMove(piece, otherPiece.position) && !piece.isInstanceOf[King]
      } yield piece).nonEmpty
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
