package elements.pieces

import actions.PiecePosition
import players.Player

trait Piece {
  def owner: Option[Player]

  def position: PiecePosition
}

case class Pawn(player: Player, position: PiecePosition) extends Piece {
  override def owner: Option[Player] = Some(player)

  override def toString: String = s"""P${player.index}"""
}


case class Knight(player: Player, position: PiecePosition) extends Piece {
  override def toString: String = s"""Kn${player.index}"""

  override def owner: Option[Player] = Some(player)
}


case class King(player: Player, position: PiecePosition) extends Piece {
  override def toString: String = s"""Ki${player.index}"""

  override def owner: Option[Player] = Some(player)

  def isDefaultPosition: Boolean = position.X == 0 && position.Y == 4
}


case class Queen(player: Player, position: PiecePosition) extends Piece {
  override def toString: String = s"""Q${player.index}"""

  override def owner: Option[Player] = Some(player)
}


case class Rook(player: Player, position: PiecePosition) extends Piece {
  override def toString: String = s"""R${player.index}"""

  override def owner: Option[Player] = Some(player)
}


case class Empty(position: PiecePosition) extends Piece {
  override def toString: String = "  "

  override def owner: Option[Player] = None
}


case class Bishop(player: Player, position: PiecePosition) extends Piece {
  override def toString: String = s"""B${player.index}"""

  override def owner: Option[Player] = Some(player)
}
