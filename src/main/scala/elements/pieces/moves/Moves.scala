package elements.pieces.moves

object Moves {
  def diagonalLeftUp = (X: Int, Y: Int) => (X - 1, Y + 1)

  def diagonalRightUp = (X: Int, Y: Int) => (X + 1, Y + 1)

  def diagonalLeftDown = (X: Int, Y: Int) => (X - 1, Y - 1)

  def diagonalRightDown = (X: Int, Y: Int) => (X - 1, Y + 1)

  def straightUp = (X: Int, Y: Int) => (X, Y + 1)

  def straightDown = (X: Int, Y: Int) => (X, Y - 1)

  def straightLeft = (X: Int, Y: Int) => (X - 1, Y)

  def straightRight = (X: Int, Y: Int) => (X + 1, Y)

}
