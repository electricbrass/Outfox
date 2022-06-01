package com.miamc.outfox

open class Cell(var chip: Chip?) {
    var isSelected = false

    fun containsChip(): Boolean {
        return chip != null
    }
}

class GoalCell(chip: Chip?, val isLightTeam: Boolean) : Cell(chip) {

    fun moveChip(board: Board) {
        val validMoves: MutableList<Cell>? = this.chip?.findValidMove(board)
    }
}