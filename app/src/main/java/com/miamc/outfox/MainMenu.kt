package com.miamc.outfox

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        val newGameButton: Button = findViewById(R.id.button)
        newGameButton.setOnClickListener { startGame() }
    }
}

fun mainMenu() {

}

fun startGame() {
    // change activity and setup game state
    val board = Board()
}