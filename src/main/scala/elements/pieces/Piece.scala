package elements.pieces

import actions.Position
import players.models.Player

sealed trait Piece {
  def reverse: Position = {
    this.position.reverse
  }

  def owner: Option[Player]

  def position: Position

  def apply(p: Position): Piece
}

case class Pawn(player: Player, position: Position) extends Piece {
  override def owner: Option[Player] = Some(player)

  override def toString: String = s"""P${player.index}"""

  def apply(p: Position): Piece = this.copy(position = p)
}


case class Knight(player: Player, position: Position) extends Piece {
  override def toString: String = s"""Kn${player.index}"""

  override def owner: Option[Player] = Some(player)

  def apply(p: Position): Piece = this.copy(position = p)
}


case class King(player: Player, position: Position) extends Piece {
  override def toString: String = s"""Ki${player.index}"""

  override def owner: Option[Player] = Some(player)

  def apply(p: Position): Piece = this.copy(position = p)

  def isDefaultPosition: Boolean = position.X == 4 && position.Y == 0
}


case class Queen(player: Player, position: Position) extends Piece {
  override def toString: String = s"""Q${player.index}"""

  override def owner: Option[Player] = Some(player)

  def apply(p: Position): Piece = this.copy(position = p)
}


case class Rook(player: Player, position: Position) extends Piece {
  override def toString: String = s"""R${player.index}"""

  override def owner: Option[Player] = Some(player)

  def apply(p: Position): Piece = this.copy(position = p)
}


case class EmptyPosition(position: Position) extends Piece {
  override def toString: String = "*"

  override def owner: Option[Player] = None

  def apply(p: Position): Piece = this.copy(position = p)
}


case class Bishop(player: Player, position: Position) extends Piece {
  override def toString: String = s"""B${player.index}"""

  override def owner: Option[Player] = Some(player)

  def apply(p: Position): Piece = this.copy(position = p)
}
