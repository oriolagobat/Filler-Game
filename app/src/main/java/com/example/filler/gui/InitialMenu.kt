package com.example.filler.gui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.filler.ChooseResults
import com.example.filler.R
import com.example.filler.databinding.ActivityMainBinding
import com.example.filler.gui.configuration.NewGameConfiguration

class InitialMenu : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Prepare the binding of the view
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set listeners to the buttons
        binding.helpButton.setOnClickListener(this)
        binding.newGameButton.setOnClickListener(this)
        binding.quitButton.setOnClickListener(this)
        // FIXME: Stub to test the result functionality
        binding.resultsStubButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        lateinit var intent: Intent

        when (v?.id) {
            R.id.helpButton -> intent = Intent(this, Help::class.java)
            R.id.newGameButton -> intent = Intent(this, NewGameConfiguration::class.java)
            R.id.quitButton -> finish()
            // FIXME: Stub to test the result functionality
            R.id.resultsStubButton -> intent = Intent(this, ChooseResults::class.java)
        }

        startActivity(intent)
    }
}
