package players

import actions.Position
import elements.boards.information.KingsPositions

trait PlayerIndex {
  def otherPlayerTurn: PlayerIndex
  def getKingPosition(kingsPositions: KingsPositions): Position
}

case object PlayerOne extends PlayerIndex {
  override def toString: String = "1"

  override def otherPlayerTurn: PlayerIndex = PlayerTwo

  override def getKingPosition(kingsPositions: KingsPositions): Position = kingsPositions.firstKingPosition
}

case object PlayerTwo extends PlayerIndex {
  override def toString: String = "2"

  override def otherPlayerTurn: PlayerIndex = PlayerOne

  override def getKingPosition(kingsPositions: KingsPositions): Position = kingsPositions.secondKingPosition
}
