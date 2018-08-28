package elements.pieces

import players.Player

case class King(player: Player) extends Piece {
  override def toString: String =
    if(player.index == 1) "Ki1"
    else "Ki2"
}
