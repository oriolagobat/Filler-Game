package com.example.filler.logic.game

import com.example.filler.constants.logic.GameColor
import com.example.filler.constants.logic.GameConstants
import com.example.filler.constants.logic.GameState
import com.example.filler.log.Logger
import com.example.filler.logic.ai.AIColorGeneratorFactoryImpl
import com.example.filler.logic.ai.AIGeneratorSettings
import com.example.filler.logic.board.Board
import com.example.filler.logic.board.BoardColorInitializer
import com.example.filler.logic.board.BoardImpl
import com.example.filler.logic.colors.ColorSelectorImpl
import com.example.filler.logic.colors.Generator
import com.example.filler.logic.colors.RandomColorGenerator
import com.example.filler.logic.player.Player
import com.example.filler.logic.player.PlayerAreaImpl
import com.example.filler.logic.score.ScoreCalculator
import com.example.filler.logic.score.ScoreCalculatorImpl

class GameFactoryImpl(private val settings: GameSettings) : GameFactory {
    private val availableColors = GameColor.values().toList().take(settings.nColors)
    private val selector = ColorSelectorImpl(availableColors)
    private lateinit var smartColorGenerator: Generator
    private lateinit var scoreCalculator: ScoreCalculator
    private lateinit var p1: Player
    private lateinit var p2: Player
    private lateinit var board: Board

    override fun makeGame(): Game {
        val data = generateInitialGameData()
        Logger.logD("Initial game data generated, creating game instance...")
        return GameImpl(scoreCalculator, smartColorGenerator, selector, board, data, p1, p2)
    }

    private fun generateInitialGameData(): GameData {
        val startingPlayer = p1
        val enemyPlayer = p2
        return GameData(startingPlayer, enemyPlayer, GameConstants.INITIAL_ROUND, GameState.P1_TURN)
    }

    init {
        initBoard()
        initPlayers()
        initScoreCalculator()
        initSelector()
        initP2AI()
    }

    private fun initBoard() {
        val randGenerator = RandomColorGenerator(availableColors)
        board = BoardImpl(settings.boardSize)
        BoardColorInitializer(availableColors, board, randGenerator).start()
        Logger.logD("Board initialized")
    }

    private fun initPlayers() {
        val player1Area = PlayerAreaImpl(board.getP1Home(), board)
        val player2Area = PlayerAreaImpl(board.getP2Home(), board)
        Logger.logD("Players initialized")
        p1 = Player(GameConstants.P1_ID, GameConstants.INITIAL_SCORE, player1Area)
        p2 = Player(GameConstants.P2_ID, GameConstants.INITIAL_SCORE, player2Area)
        Logger.logD("Player areas initialized")
    }

    private fun initScoreCalculator() {
        scoreCalculator = ScoreCalculatorImpl(board, p1.area, p2.area)
        Logger.logD("Score calculator initialized")
    }

    private fun initP2AI() {
        val settingsForAI = AIGeneratorSettings(board, p1.area, p2.area, selector)
        smartColorGenerator = AIColorGeneratorFactoryImpl()
            .makeGenerator(this.settings.difficulty, settingsForAI)
        Logger.logD("AI initialized")
    }

    private fun initSelector() {
        selector.select(board.getColor(board.getP1Home()))
        selector.select(board.getColor(board.getP2Home()))
        Logger.logD("Color selector initialized")
    }
}
