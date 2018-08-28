package elements.pieces

import players.Player

case class Queen(player: Player) extends Piece {
  override def toString: String =
    if(player.index == 1) "Q1"
    else "Q2"
}
