package elements.pieces

import players.Player

trait Piece {
  def owner: Option[Player]
}