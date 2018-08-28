package elements.pieces

import actions.PiecePosition
import players.Player

case class Empty(piecePosition: PiecePosition) extends Piece {
  override def toString: String = "  "

  override def owner: Option[Player] = None
}

