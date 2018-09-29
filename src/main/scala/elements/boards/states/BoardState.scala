package elements.boards.states

import actions.Position
import actions.validators.board.BoardQueries
import config.Config
import elements.boards.PreviousMove
import elements.boards.information.KingsPositions
import elements.pieces.{EmptyPosition, King, Piece}
import players.{PlayerOne, PlayerTwo, Players}
import validator.{Failure, Success, Validator}
import actions.validators.board.BoardQueries.BoardQueriesImplicit
import validator.ValidatorConverterImplicits.toBoolean

trait BoardState {
  def players: Players

  def previousMove: PreviousMove = ???

  def getKingPositionForCurrentPlayer: Position = {
    players.getPlayerTurn.index match {
      case PlayerOne => kingsPositions.firstKingPosition
      case PlayerTwo => kingsPositions.secondKingPosition
    }
  }

  def getKingForCurrentPlayer: King = {
    players.getPlayerTurn.index match {
      case PlayerOne => getPiece(kingsPositions.firstKingPosition.X, kingsPositions.firstKingPosition.Y).asInstanceOf[King]
      case PlayerTwo => getPiece(kingsPositions.secondKingPosition.X, kingsPositions.secondKingPosition.Y).asInstanceOf[King]
    }
  }

  def getPiece(X: Int, Y: Int): Piece = this.pieces(Y)(X)

  def swapPlayerTurn: Players = players.copy(playerTurn = players.otherPlayerTurn)

  def kingsPositions: KingsPositions = KingsPositions(pieces)

  def pieces: List[List[Piece]]

  def next: Option[BoardState]

  def isPositionFree(position: Position): Validator = {
    getPiece(position.X, position.Y) match {
      case _: EmptyPosition => Success(this)
      case _ => Failure(Config.positionNotFreeMessage, this)
    }
  }

  override def toString: String = {
    val rows = pieces.zipWithIndex.map {
      r =>
        s"""${BoardQueries.numberMapping(r._2)}| ${
          r._1.foldLeft("")((res, curr) =>
            res + curr.toString + (" " * (4 + (2 - curr.toString.length))))
        }"""
    }.reverse

    val finalBoard = rows :+ s" |  ${0 to 7 mkString "     "}"

    finalBoard.mkString("\n")//.replace(" ", "-")
  }
}

object BoardState {
  def nextPhase(pieces: List[List[Piece]], players: Players): BoardState = {
    val dummyBoard = DummyBoard(pieces, players)

    //TODO improve this
    Validator.toBoardState(dummyBoard.isStall, pieces, players, StalemateState) orElse
    Validator.toBoardState(dummyBoard.isEndGame, pieces, players, CheckmateState) orElse
    Validator.toBoardState(dummyBoard.isKingInCheck, pieces, players, CheckState) orElse
    Some(NormalState(pieces, players)) match {
      case Some(board) => board
    }
  }
}