package players

import actions.{Move, Position}

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

case class HumanPlayer(name: String, index: PlayerIndex) extends Player {
  //TODO testing
  override def askForMove: Move = {
    def getAPosition(msg: String, position: String): Option[Position] = {
      def getValidMove(msg: String): Option[Position] = {
        def getMove(msg: String): Try[Option[Position]] =
          Try {
            val from = readLine(msg)
            Position(from)
          }

        getMove(msg) match {
          case Success(piece) => piece
          case Failure(_) => getValidMove(s"Invalid $position move format. Please choose again: \n")
        }
      }

      getValidMove(msg)
    }

    (
      getAPosition("Choose a piece to move\n", "starting"),
      getAPosition("Choose a destination\n", "ending")
    ) match {
      case (Some(from), Some(to)) => Move(from, to)
      case (None, _)              => println("Invalid starting position. Choose again.\n"); askForMove
      case (Some(_), None)        => println("Invalid end position. Choose again. \n"); askForMove
    }
  }
}
