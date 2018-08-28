package elements.pieces

import actions.PiecePosition
import players.Player

case class Queen(player: Player, piecePosition: PiecePosition) extends Piece {
  override def toString: String =
    if(player.index == 1) "Q1"
    else "Q2"

  override def owner: Option[Player] = Some(player)
}
