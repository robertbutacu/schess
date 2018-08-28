package elements.pieces

import actions.PiecePosition
import players.Player

case class Knight(player: Player, piecePosition: PiecePosition) extends Piece {
  override def toString: String =
    if(player.index == 1) "Kn1"
    else "Kn2"

  override def owner: Option[Player] = Some(player)
}