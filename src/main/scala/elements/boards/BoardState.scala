package elements.boards

import actions.Position
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.{EmptyPosition, Piece}
import players.{Player, PlayerOne, PlayerTwo}

trait BoardState {
  def players: Players

  def previousMove: PreviousMove = ???

  def getKingPositionForCurrentPlayer: Position = {
    players.getPlayerTurn.index match {
      case PlayerOne => kingsPositions.firstKingPosition
      case PlayerTwo => kingsPositions.secondKingPosition
    }
  }

  def kingsPositions: KingsPositions

  def pieces: List[List[Piece]]

  def isPlayerKingUncovered: Boolean

  def revertBoard: List[List[Piece]] = this.pieces.reverse

  def next: Option[BoardState]

  def isPositionFree(position: Position): Boolean = {
    pieces(position.Y)(position.X) match {
      case _: EmptyPosition => true
      case _        => false
    }
  }

  //TODO test
  def isPieceOwner[P <: Piece](position: Position, owner: Player) =
    pieces(position.Y)(position.X) match {
      case p: P => val result = p.owner.forall(p => p == owner)
        result
      case _    => false
    }
}
