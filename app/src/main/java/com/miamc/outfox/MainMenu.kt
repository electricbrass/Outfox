package com.miamc.outfox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        val newGameButton: Button = findViewById(R.id.newGameButton)
        newGameButton.setOnClickListener { startGame(true) }
        val loadGameButton: Button = findViewById(R.id.loadGameButton)
        loadGameButton.setOnClickListener { startGame(false) }
    }

    private fun startGame(newGame: Boolean) {
        // change activity and setup game state
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("newgame", newGame);
        startActivity(i)
    }
}