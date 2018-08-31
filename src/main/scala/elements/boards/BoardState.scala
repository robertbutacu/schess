package elements.boards

import actions.PiecePosition
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.{Empty, Piece}
import players.Player

trait BoardState {
  def players: Players

  def kingsPositions: KingsPositions

  def pieces: List[List[Piece]]

  def isPlayerKingUncovered: Boolean

  def revertBoard: List[List[Piece]] = this.pieces.reverse

  def next: Option[BoardState]

  def isPositionFree(position: PiecePosition): Boolean = {
    pieces(position.Y)(position.X) match {
      case _: Empty => true
      case _        => false
    }
  }

  def isPieceInstance[P <: Piece](position: PiecePosition, owner: Player) =
    pieces(position.Y)(position.X) match {
      case p: P => val result = p.owner.forall(p => p == owner)
        result
      case _    => false
    }
}
