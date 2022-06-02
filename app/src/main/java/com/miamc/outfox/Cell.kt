package com.miamc.outfox

open class Cell(var chip: Chip?, val row: Int, val column: Int) {

    fun containsChip(): Boolean {
        return chip != null
    }
}

class GoalCell(chip: Chip?, row: Int, column: Int, val isLightTeam: Boolean) : Cell(chip, row, column) {

    fun moveChip(board: Board) {
        val validMoves: MutableSet<Cell>? = this.chip?.findValidMove(board, this)
    }
}