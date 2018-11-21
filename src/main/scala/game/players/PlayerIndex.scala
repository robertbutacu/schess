package game.players

import game.elements.boards.Position
import game.elements.boards.information.KingsPositions

sealed trait PlayerIndex {
  def otherPlayerTurn: PlayerIndex
  def getKingPosition(kingsPositions: KingsPositions): Position
  def toInt: Int
}

case object PlayerOne extends PlayerIndex {
  override def toString: String = "1"

  override def otherPlayerTurn: PlayerIndex = PlayerTwo

  override def getKingPosition(kingsPositions: KingsPositions): Position = kingsPositions.firstKingPosition

  override def toInt: Int = 1
}

case object PlayerTwo extends PlayerIndex {
  override def toString: String = "2"

  override def otherPlayerTurn: PlayerIndex = PlayerOne

  override def getKingPosition(kingsPositions: KingsPositions): Position = kingsPositions.secondKingPosition

  override def toInt: Int = 2
}

