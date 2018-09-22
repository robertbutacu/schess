import game.PlayChess
import players.{PlayerOne, PlayerTwo, Players}
import players.models.{AIPlayer, HumanPlayer}

object Main extends App {
  //PlayChess.initialBoardState(AIPlayer("X", PlayerOne), AIPlayer("Y", PlayerTwo)).foreach(println)
  println(PlayChess.initiateGame.toString)
}
