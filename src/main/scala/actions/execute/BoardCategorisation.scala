package actions.execute

import actions.validators.board.BoardCheckers._
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import actions.validators.moves.MoveValidator._
import actions.validators.moves.MoveValidator.ops._
import actions.validators.moves.{Moves, PathConstructor}
import game.elements.boards.Position
import game.elements.boards.states.BoardState
import game.elements.pieces.{King, Knight, Piece}
import validator.ValidatorConverterImplicits.toBoolean

object BoardCategorisation {
  def isCheckmate(board: BoardState): Boolean = {
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

    if(isCheck(board)) isKingBlocked || canKingBeCovered
    else               areThereOtherMovesAvailable(board)
  }

  def areThereOtherMovesAvailable(board: BoardState): Boolean = {
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

  def isCheck(board: BoardState): Boolean = {
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

  // player is not in check, but for all moves, it would result a check
  def isStalemate(board: BoardState): Boolean = {
    val possibleMoves = for {
      row                 <- board.pieces
      piece               <- row
      if piece.owner.forall(p => p.index.toInt == board.players.playerTurn)
      possibleMoveRow     <- board.pieces
      possibleEndPosition <- possibleMoveRow
      if board.isValidMove(piece, possibleEndPosition.position)
      updatedBoard = MoveCategorisation.categorise(board, piece.position, possibleEndPosition.position)
      if updatedBoard.isKingNotInCheck
    } yield piece

    possibleMoves.isEmpty
  }

  def invertBoard(pieces: List[List[Piece]]): List[List[Piece]] = {
    pieces.reverse.map{row =>
      row.map{piece => piece(piece.reverse)}
    }
  }
}
