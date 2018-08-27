package actions

case class PiecePosition(X: Int, Y: Int) {
  def isInBounds(i: Int) = i >= 0 && i <= 7

  require(isInBounds(X) && isInBounds(Y))
}
