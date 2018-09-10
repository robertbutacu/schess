package elements.boards

import actions.execute.MoveCategorisation
import actions.{Move, Position}
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.{EmptyPosition, King, Piece}
import players.{PlayerOne, PlayerTwo}
import elements.boards.validators.BoardValidators.BoardValidator

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

  def swapPlayerTurn = players.copy(playerTurn = players.otherPlayerTurn)

  def kingsPositions: KingsPositions = KingsPositions(pieces)

  def pieces: List[List[Piece]]

  def wouldPlayerKingBeInCheck(givenMove: Move): Boolean = {
    val possibleBoardUpdated = MoveCategorisation.categorise(this, givenMove.from, givenMove.to)

    possibleBoardUpdated.isKingInCheckState(players.getPlayerTurn.index,
      KingsPositions(possibleBoardUpdated.pieces))
  }

  def revertBoard: List[List[Piece]] = this.pieces.reverse

  def next: Option[BoardState]

  def isPositionFree(position: Position): Boolean = {
    pieces(position.Y)(position.X) match {
      case _: EmptyPosition => true
      case _ => false
    }
  }
}
