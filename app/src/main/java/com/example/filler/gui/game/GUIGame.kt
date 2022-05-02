package com.example.filler.gui.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filler.constants.Difficulty
import com.example.filler.constants.PlayerType
import com.example.filler.databinding.ActivityGameBinding
import com.example.filler.logic.game.Game
import com.example.filler.logic.game.GameFactoryImpl
import com.example.filler.logic.game.GameResponse
import com.example.filler.logic.game.GameSettings

class GUIGame : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set this activity binding
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val colorNum = intent.getIntExtra("colorNum", 0)
        val gridNum = intent.getIntExtra("gridNum", 0)
        val timeControl = intent.getBooleanExtra("timeControl", false)
        val difficultyString = intent.getStringExtra("difficulty")
        // Parse difficulty to constant value
        val difficulty = Difficulty.valueOf(difficultyString!!.uppercase())

        val settings = GameSettings(gridNum, colorNum, difficulty)

        // Initialize usernames and timer
        setUpTimersAndUsernames(username!!)

        // Initialize game
        game = GameFactoryImpl(settings).makeGame()

        // First Iteration
        firstGameIteration()
    }

    private fun setUpTimersAndUsernames(username: String) {
        binding.usernameText.text = username
    }

    // Starts the game, making a first iteration to initialize everything
    private fun firstGameIteration() {
        val firstGameResponse = game.getGameResponse()
        val firstPlayer = PlayerType.HUMAN
        GameIteration(this, binding, game, firstGameResponse, firstPlayer).start()
    }

    fun nextIteration(gameResponse: GameResponse, nextPlayer: PlayerType) {
        GameIteration(this, binding, game, gameResponse, nextPlayer).start()
    }
}
