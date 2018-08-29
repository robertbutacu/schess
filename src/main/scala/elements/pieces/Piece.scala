package elements.pieces

import actions.PiecePosition
import players.Player

trait Piece {
  def owner: Option[Player]

  def piecePosition: PiecePosition
}

case class Pawn(player: Player, piecePosition: PiecePosition) extends Piece {
  override def owner: Option[Player] = Some(player)

  override def toString: String =
    if (player.index == 1) "P1"
    else "P2"
}

case class Knight(player: Player, piecePosition: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "Kn1"
    else "Kn2"

  override def owner: Option[Player] = Some(player)
}

case class King(player: Player, piecePosition: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "Ki1"
    else "Ki2"

  override def owner: Option[Player] = Some(player)
}

case class Queen(player: Player, piecePosition: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "Q1"
    else "Q2"

  override def owner: Option[Player] = Some(player)
}

case class Rook(player: Player, piecePosition: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "R1"
    else "R2"

  override def owner: Option[Player] = Some(player)
}

case class Empty(piecePosition: PiecePosition) extends Piece {
  override def toString: String = "  "

  override def owner: Option[Player] = None
}

case class Bishop(player: Player, piecePosition: PiecePosition) extends Piece {
  override def toString: String =
    if (player.index == 1) "B1"
    else "B2"

  override def owner: Option[Player] = Some(player)
}
