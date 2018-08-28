package elements.pieces

import actions.PiecePosition
import players.Player

case class Bishop(player: Player, piecePosition: PiecePosition) extends Piece {
  override def toString: String =
    if(player.index == 1)  "B1"
    else "B2"

  override def owner: Option[Player] = Some(player)
}
