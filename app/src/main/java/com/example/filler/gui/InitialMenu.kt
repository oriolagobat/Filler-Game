package com.example.filler.gui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.filler.R
import com.example.filler.databinding.ActivityMainBinding

class InitialMenu : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Prepare the binding of the view
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set listeners to the buttons
        binding.helpButton.setOnClickListener(this)
        binding.newGameButton.setOnClickListener(this)
        binding.quitButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        lateinit var intent: Intent

        when (v?.id) {
            R.id.helpButton -> intent = Intent(this, Help::class.java)
            R.id.newGameButton -> intent = Intent(this, NewGameConfiguration::class.java)
            R.id.quitButton -> finish()
        }

        startActivity(intent)
    }
}