package players

trait PlayerIndex

case object PlayerOne extends PlayerIndex {
  override def toString: String = "1"
}

case object PlayerTwo extends PlayerIndex {
  override def toString: String = "2"
}

