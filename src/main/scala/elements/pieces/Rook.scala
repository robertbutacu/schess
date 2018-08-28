package elements.pieces

import players.Player

case class Rook(player: Player) extends Piece {
  override def toString: String =
    if(player.index == 1) "R1"
    else "R2"

  override def owner: Option[Player] = Some(player)
}