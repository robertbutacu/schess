package players

import players.models.Player

case class Players(player1: Player, player2: Player, playerTurn: Int) {
  def getPlayerTurn: Player = if (playerTurn == 1) player1 else player2

  def getOtherPlayerTurn: Player = if (playerTurn == 1) player2 else player1

  def otherPlayerTurn: Int = 3 - playerTurn

  def switchTurns: Players = this.copy(playerTurn = this.otherPlayerTurn)
}