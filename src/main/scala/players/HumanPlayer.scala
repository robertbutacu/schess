package players

import actions.Move
import scala.io.StdIn.readLine

case class HumanPlayer(name: String, index: Int) extends Player {
  override def askForMove: Move = {
    def getValidMove(): Move = {
      val from = readLine("Choose the piece you want to move: \n\n")

      ???
    }
    ???
  }
}
