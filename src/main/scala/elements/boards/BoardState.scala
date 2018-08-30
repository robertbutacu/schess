package elements.boards

import actions.PiecePosition
import elements.boards.information.{KingsPositions, Players}
import elements.pieces.{Empty, Piece}

trait BoardState {
  def players: Players

  def kingsPositions: KingsPositions

  def pieces: List[List[Piece]]

  def isPlayerKingUncovered: Boolean

  def revertBoard: List[List[Piece]] = this.pieces.reverse

  def next: Option[BoardState]

  def isPositionFree(position: PiecePosition): Boolean = {
    pieces(position.X)(position.Y) match {
      case _: Empty => true
      case _        => false
    }
  }
}
