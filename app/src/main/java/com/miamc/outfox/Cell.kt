package com.miamc.outfox

open class Cell(var chip: Chip?) {

}

class GoalCell(chip: Chip?, val isLightTeam: Boolean) : Cell(chip) {
    fun moveChip(board: Board) {
        val validMoves: MutableList<Cell>? = this.chip?.findValidMove(board)
    }
}