package errors

sealed trait ChessError

case object IllegalMove extends ChessError