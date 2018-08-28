package elements.pieces

import players.Player

case class Bishop(player: Player) extends Piece {
  override def toString: String =
    if(player.index == 1)  "B1"
    else "B2"
}
