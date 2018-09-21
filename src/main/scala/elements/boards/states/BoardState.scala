package elements.boards.states

import actions.Position
import elements.boards.PreviousMove
import elements.boards.information.KingsPositions
import elements.pieces.{EmptyPosition, King, Piece}
import players.{PlayerOne, PlayerTwo, Players}

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
      case PlayerOne => pieces(kingsPositions.firstKingPosition.Y)(kingsPositions.firstKingPosition.X).asInstanceOf[King]
      case PlayerTwo => pieces(kingsPositions.secondKingPosition.Y)(kingsPositions.secondKingPosition.X).asInstanceOf[King]
    }
  }

  def swapPlayerTurn: Players = players.copy(playerTurn = players.otherPlayerTurn)

  def kingsPositions: KingsPositions = KingsPositions(pieces)

  def pieces: List[List[Piece]]

  def revertBoard: List[List[Piece]] = this.pieces.reverse

  def next: Option[BoardState]

  def isPositionFree(position: Position): Boolean = {
    pieces(position.Y)(position.X) match {
      case _: EmptyPosition => true
      case _ => false
    }
  }
}
