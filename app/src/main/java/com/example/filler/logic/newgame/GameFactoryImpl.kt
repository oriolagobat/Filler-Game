package com.example.filler.logic.newgame

import com.example.filler.constants.GameColor
import com.example.filler.constants.GameConstants
import com.example.filler.constants.GameData
import com.example.filler.constants.GameState
import com.example.filler.logic.ai.AIColorGeneratorFactoryImpl
import com.example.filler.logic.ai.AIGeneratorSettings
import com.example.filler.logic.board.Board
import com.example.filler.logic.board.BoardColorInitializer
import com.example.filler.logic.board.BoardImpl
import com.example.filler.logic.colors.ColorSelectorImpl
import com.example.filler.logic.colors.RandomColorGenerator
import com.example.filler.logic.game.GameSettings
import com.example.filler.logic.game.Generator
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

    override fun makeGame(settings: GameSettings): Game {
        val data = generateInitialGameData()
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
    }

    private fun initPlayers() {
        val player1Area = PlayerAreaImpl(board.getP1Home(), board)
        val player2Area = PlayerAreaImpl(board.getP2Home(), board)
        p1 = Player(GameConstants.INITIAL_SCORE, player1Area)
        p2 = Player(GameConstants.INITIAL_SCORE, player2Area)
    }

    private fun initScoreCalculator() {
        scoreCalculator = ScoreCalculatorImpl(board, p1.area, p2.area)
    }

    private fun initP2AI() {
        val settingsForAI = AIGeneratorSettings(board, p2.area, availableColors)
        smartColorGenerator = AIColorGeneratorFactoryImpl()
            .makeGenerator(this.settings.difficulty, settingsForAI)
    }

    private fun initSelector() {
        selector.select(board.getColor(board.getP1Home()))
        selector.select(board.getColor(board.getP2Home()))
    }
}
