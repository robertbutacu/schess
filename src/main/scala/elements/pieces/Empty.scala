package elements.pieces

import players.Player

case object Empty extends Piece {
  override def toString: String = "  "

  override def owner: Option[Player] = None
}

