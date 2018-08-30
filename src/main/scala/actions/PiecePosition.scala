package actions

case class PiecePosition(X: Int, Y: Int) {
  def isInBounds(i: Int) = i >= 0 && i <= 7

  def apply(increment: (Int, Int) => (Int, Int)) = PiecePosition(increment(X, Y))

  require(isInBounds(X) && isInBounds(Y))
}

object PiecePosition {
  def apply(position: (Int, Int)): PiecePosition = PiecePosition(position._1, position._2)
}
