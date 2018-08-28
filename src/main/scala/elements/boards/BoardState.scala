package elements.boards

import elements.boards.information.{KingsPositions, Players}
import elements.pieces.Piece

trait BoardState {
  def players: Players
  def kingsPositions: KingsPositions

  def pieces: List[List[Piece]]

  def isPlayerKingUncovered: Boolean

  def revertBoard: List[List[Piece]] = this.pieces.reverse

  def next: BoardState
}
