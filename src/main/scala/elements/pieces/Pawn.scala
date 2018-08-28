package elements.pieces

import actions.PiecePosition
import players.Player

case class Pawn(player: Player, piecePosition: PiecePosition) extends Piece {
  override def owner: Option[Player] = Some(player)

  override def toString: String =
    if(player.index == 1) "P1"
    else "P2"
}
