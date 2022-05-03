package com.example.filler.gui.homepage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.filler.R
import com.example.filler.databinding.ActivityMainBinding
import com.example.filler.gui.configuration.NewGameConfiguration
import com.example.filler.gui.help.Help
import com.example.filler.gui.hideNavBar
import com.google.android.material.color.DynamicColors

class InitialMenu : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the dynamic colors
        DynamicColors.applyToActivitiesIfAvailable(application)

        // Hide the navbar
        hideNavBar(this)

        // Get the binding and set the view
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set listeners to the buttons
        binding.helpButton.setOnClickListener(this)
        binding.newGameButton.setOnClickListener(this)
        binding.quitButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        val intent = when (v?.id) {
            R.id.helpButton -> Intent(this, Help::class.java)
            R.id.newGameButton -> Intent(this, NewGameConfiguration::class.java)
            R.id.quitButton -> null
            else -> throw IllegalArgumentException("No more buttons")
        }

        if (intent != null) startActivity(intent) else finish()
    }
}
