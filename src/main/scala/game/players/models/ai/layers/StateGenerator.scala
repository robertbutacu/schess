package game.players.models.ai.layers

import game.elements.boards.states.BoardState
import scala.concurrent.Future

case class Layer(currentState: BoardState, children: List[Layer])

object StateGenerator {
  //TODO maybe use Tree
  def generate(state: BoardState)(implicit difficulty: Difficulty): Future[List[Layer]] = {
    def go(state: Layer, currentLayer: Int = 0): Future[Layer] = {
      if(currentLayer == difficulty.maxDepth)
        ???
      else {
        ???
      }
    }
    ???
  }
}