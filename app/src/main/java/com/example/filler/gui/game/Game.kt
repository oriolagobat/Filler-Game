package com.example.filler.gui.game

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.filler.constants.Difficulty
import com.example.filler.constants.GameColor
import com.example.filler.databinding.ActivityGameBinding
import com.example.filler.logic.GameResponse
import com.example.filler.logic.GameSettings
import com.example.filler.logic.stub.GameStub3x3
import com.example.filler.logic.stub.GameStub9x9

class Game : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

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

        val message =
            "Username: $username\n" +
                    "Color: $colorNum\n" +
                    "Grid: $gridNum\n" +
                    "Time Control: $timeControl\n" +
                    "Difficulty: $difficulty"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // This is how it will be
        val settings = GameSettings(gridNum, colorNum, difficulty)

        // Initialize usernames and timer
        setUpTimersAndUsernames(username!!)

        // Initialize board
        setUpGameBoard(settings)

        // Initialize chooser bar
        setUpChooserBar(settings)
    }

    private fun setUpTimersAndUsernames(username: String) {
        binding.playerName.text = username
    }

    // FIXME: Remove stub functionality and replace with actual game logic
    private fun setUpGameBoard(settings: GameSettings) {
        // This is how it will be
//        binding.boardGridView.numColumns = settings.boardSize
        // This is for now, in order to work  with the game stub
//        binding.boardGridView.numColumns = 3
        binding.boardGridView.numColumns = 9
//        val gameStub = GameStub3x3(settings)  //  Game initialization
        val gameStub = GameStub9x9(settings)  //  Game initialization

        val stubBoard: GameResponse = gameStub.initGame()
        binding.boardGridView.adapter =
            GridAdapter(this, stubBoard.board.toArray(), binding.boardGridView)
    }
    private fun setUpChooserBar(settings: GameSettings) {
        // This is how it will be
//        binding.boardGridView.numColumns = settings.boardSize
        // This is for now, in order to work  with the game stub
//        binding.selectorGridView.numColumns = 3
        binding.selectorGridView.numColumns = 9
//        val gameStub = GameStub3x3(settings)  //  Game initialization
        val gameStub = GameStub9x9(settings)  //  Game initialization

        val stubSelector: GameResponse = gameStub.initGame()
        val (colors, selected) = unpackArray(stubSelector.selector.toArray())
        binding.selectorGridView.adapter =
            GridAdapter(this, colors, binding.selectorGridView)
    }

    private fun unpackArray(array: Array<Pair<GameColor, Boolean>>): Pair<Array<GameColor>, Array<Boolean>> {
        val colors = array.map { it.first }.toTypedArray()
        val selected = array.map { it.second }.toTypedArray()
        print("Colors: ${colors.contentToString()}")
        print("Selected: ${selected.contentToString()}")
        return Pair(colors, selected)
    }
}
