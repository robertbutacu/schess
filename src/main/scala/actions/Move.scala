package actions

import pieces.Piece

case class Move(piece: Piece, from: PiecePosition, to: PiecePosition)
