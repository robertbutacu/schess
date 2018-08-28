package elements.boards.information

import players.Player

case class Players(player1: Player, player2: Player, playerTurn: Int) {
  def nextPlayer: Players = this.copy(playerTurn = 3 - this.playerTurn)
}
