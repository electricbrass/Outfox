package com.miamc.outfox

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val boardView: BoardView = findViewById(R.id.boardView)
        boardView.setOnClickListener() { this.gameWon() }
    }

    private fun gameWon() {
        if (board.gameWon()) {
            val gameStatus: TextView = findViewById(R.id.gameStatus)
            if (board.isLightTurn) {
                gameStatus.text = "Dark Team Won"
            } else {
                gameStatus.text = "Light Team Won"
            }
        }
    }

    val board = Board()
}