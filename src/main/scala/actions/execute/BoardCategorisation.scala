package actions.execute

import actions.validators.board.BoardCheckers._
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import actions.validators.moves.MoveValidator._
import actions.validators.moves.MoveValidator.ops._
import elements.boards.states.BoardState
import elements.pieces.{King, Piece}
import validator.ValidatorConverterImplicits.toBoolean

object BoardCategorisation {
  //TODO account for situations where the king can be protected by other pieces
  def isCheckmate(board: BoardState): Boolean = {
    val playerTurnIndex = board.players.getPlayerTurn.index

    def isEnemyPlayer(p: Piece) = p.owner.forall(player => player.index != playerTurnIndex)

    def areAllPossiblePositionsBlocked: Boolean = {
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

    if(isCheck(board))
      areAllPossiblePositionsBlocked
    else {false
      //if(areThereOtherMovesAvailable())
    }


    !areThereOtherMovesAvailable(board) || areAllPossiblePositionsBlocked
  }

  def areThereOtherMovesAvailable(board: BoardState): Boolean = {
    val playerTurnIndex = board.players.getPlayerTurn.index

    (for {
      row <- board.pieces
      piece <- row
      toRow <- board.pieces
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
      row <- board.pieces
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
