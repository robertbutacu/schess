package players

import actions.{Move, PiecePosition}

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

case class HumanPlayer(name: String, index: Int) extends Player {
  override def askForMove: Move = {
    def getValidMove(msg: String): Move = {
      def getMove(msg: String) =
        Try {
          val from = readLine(msg)
          PiecePosition(from)
        }

      getMove(msg) match {
        case Success(piece) => ???
        case Failure(_) => getValidMove("Invalid move. Please choose again: \n")
      }
    }

    getValidMove("Choose the piece you want to move: \n")
  }
}
