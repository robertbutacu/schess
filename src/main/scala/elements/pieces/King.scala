package elements.pieces

import actions.PiecePosition
import players.Player

case class King(player: Player, piecePosition: PiecePosition) extends Piece {
  override def toString: String =
    if(player.index == 1) "Ki1"
    else "Ki2"

  override def owner: Option[Player] = Some(player)
}
