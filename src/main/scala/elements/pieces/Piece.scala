package elements.pieces

import actions.PiecePosition
import players.Player

trait Piece {
  def owner: Option[Player]

  def position: PiecePosition
}

case class Pawn(player: Player, position: PiecePosition) extends Piece {
  override def owner: Option[Player] = Some(player)

  override def toString: String =
    if (player.index == 1) "P1"
    else "P2"
}

case class Knight(player: Player, position: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "Kn1"
    else "Kn2"

  override def owner: Option[Player] = Some(player)
}

case class King(player: Player, position: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "Ki1"
    else "Ki2"

  override def owner: Option[Player] = Some(player)
}

case class Queen(player: Player, position: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "Q1"
    else "Q2"

  override def owner: Option[Player] = Some(player)
}

case class Rook(player: Player, position: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "R1"
    else "R2"

  override def owner: Option[Player] = Some(player)
}

case class Empty(position: PiecePosition) extends Piece {
  override def toString: String = "  "

  override def owner: Option[Player] = None
}

case class Bishop(player: Player, position: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "B1"
    else "B2"

  override def owner: Option[Player] = Some(player)
}
