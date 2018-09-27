package config

import com.typesafe.config.ConfigFactory

object Config {
  private lazy val configFactory = ConfigFactory.load("messages.conf")

  lazy val emptyPositionMessage: String = configFactory.getString("board.errors.empty-position")
  lazy val enemyRookMessage: String = configFactory.getString("board.errors.enemy-rook")
  lazy val notRookMessage: String = configFactory.getString("board.errors.not-rook")
  lazy val notVerticalMoveMessage: String = configFactory.getString("board.errors.not-vertical-move")
  lazy val amongAllMovesMessage: String = configFactory.getString("board.errors.among-all-moves")
  lazy val positionNotFreeMessage: String = configFactory.getString("board.errors.position-not-free")
  lazy val endPositionOwnPieceMessage: String = configFactory.getString("board.errors.end-position-own-piece")
  lazy val kingPositionMessage: String = configFactory.getString("board.errors.king-position")
}
