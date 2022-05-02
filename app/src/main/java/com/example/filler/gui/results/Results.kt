package com.example.filler.gui.results

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.filler.R
import com.example.filler.databinding.ActivityResultsBinding
import com.example.filler.gui.configuration.NewGameConfiguration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Results : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityResultsBinding

    // Start necessary variables
    private var email: String? = null
    private lateinit var date: String
    private lateinit var log: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.logOutput.movementMethod = ScrollingMovementMethod()  // Makes the log scrollable
        val resultType = intent.getStringExtra("resultType")

        // Start the media player with the sound corresponding to the outcome of the game
        startSongPlayer(this, intent)

        // Set the corresponding layout image and text corresponding to the outcome of the game
        val (imageId, text) = when (resultType) {
            "win" -> Pair(R.drawable.result_win, R.string.results_win_header)
            "loose" -> Pair(R.drawable.result_lose, R.string.results_lose_header)
            "draw" -> Pair(R.drawable.result_draw, R.string.results_draw_header)
            else -> throw IllegalArgumentException("No more possible results")  // Will never be thrown
        }
        updateOutcomeTextImage(imageId, text)

        // Set the date and log of the game
        setCurrentDate()
        setLog()


        // Set listeners
        setUpResultListeners(this, binding)
    }

    private fun updateOutcomeTextImage(imageId: Int, textId: Int) {
        binding.outcomeImage.setImageResource(imageId)
        val text = getString(textId)
        binding.outcomeHeader.text = text
    }

    // TODO: Should this be my job or the logic one?
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCurrentDate() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        date = current.format(formatter)
        binding.dateTimeOutput.text = date
    }

    private fun setLog() {
        // TODO: Get the log
        // FIXME: Stub functionality
        log = getString(R.string.stub_log)
        binding.logOutput.text = log
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.emailInput -> getEmail()
            R.id.sendEmailButton -> sendEmail()
            R.id.restartGameButton -> restartGame()
            R.id.closeButton -> finish()
        }
    }

    private fun getEmail() {
        email = saveEmail(this, binding.emailInput)
    }

    private fun sendEmail() {
        getEmail()
        checkAndSendMail(this, email, date, log)
    }

    // TODO: Check stack
    private fun restartGame() {
        val intent = Intent(this, NewGameConfiguration::class.java)
        startActivity(intent)
    }
}