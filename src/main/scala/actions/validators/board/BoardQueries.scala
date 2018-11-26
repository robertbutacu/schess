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
  val letterMapping: Map[Char, Int] = Map('A' -> 0, 'B' -> 1, 'C' -> 2, 'D' -> 3, 'E' -> 4, 'F' -> 5, 'G' -> 6, 'H' -> 7)
  val numberMapping: Map[Int, Char] = letterMapping.map(_.swap)

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

    def isCheckmate: Boolean = {
      val playerTurnIndex = board.players.getPlayerTurn.index

      def isKingBlocked: Boolean = {
        val playerKing = board.getKingForCurrentPlayer

        val possibleMoves = for {
          row   <- board.pieces
          piece <- row
          if board.isValidMove(playerKing, piece.position)
        } yield board.getPossiblePositionForKing(playerKing, piece.position)

        possibleMoves.isEmpty
      }

      def isKingUncovered: Boolean = {
        val playerKing = board.getKingForCurrentPlayer

        val pieces = for {
          row        <- board.pieces
          enemyPiece <- row

          if board.isValidMove(enemyPiece, playerKing.position)

          incrementFunction = Moves.moveType(enemyPiece.position, playerKing.position)
          trajectory        = PathConstructor.construct(board, enemyPiece, playerKing.position, incrementFunction)

          allyRow    <- board.pieces
          allyPiece  <- allyRow

          if allyPiece.owner.contains(board.players.getPlayerTurn)
          if trajectory.exists{case (x, y) => board.isValidMove(allyPiece, Position(x, y))}
        } yield allyPiece

        pieces.isEmpty
      }

      //in order for it to be checkmate, the king would have to be both uncovered by other pieces and blocked in his position
      isCheck && isKingUncovered && isKingBlocked
    }

    def isNotCheck: Validator = Validator.toValidate(!isCheck, Config.kingInCheckMessage, board)

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
      val enemyPlayer = board.players.getOtherPlayerTurn
      val kingPosition = board.getKingPositionForCurrentPlayer

      val threateningPieces = for {
        row   <- board.pieces
        piece <- row
        if piece.owner.contains(enemyPlayer)
        if board.isValidMove(piece, kingPosition)
      } yield piece

      threateningPieces.nonEmpty
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
