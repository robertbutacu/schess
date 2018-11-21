package actions.execute

import game.elements.pieces.Piece

object BoardCategorisation {
  def invertBoard(pieces: List[List[Piece]]): List[List[Piece]] = {
    pieces.reverse.map{row =>
      row.map{piece => piece(piece.reverse)}
    }
  }
}
