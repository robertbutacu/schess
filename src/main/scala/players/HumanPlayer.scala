package players

import actions.Move

case class HumanPlayer(name: String, index: Int) extends Player {
  override def askForMove: Move = ???
}
