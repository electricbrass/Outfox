package com.miamc.outfox

open class Chip(val onLightTeam: Boolean) {
    private var inGoal = false

    /* returns true if cell is not a possible destination for a move, either due to having a chip
    * already or being in the opponents goal area
    * */
    protected fun isNotValidMove(cell: Cell): Boolean {
        return (cell.containsChip() ||
                ((cell is GoalCell) && (cell.isLightTeam != this.onLightTeam)))
    }

    open fun findValidMove(board: Board, cell: Cell): MutableList<Cell> {
        val validMoves = mutableListOf<Cell>()
        val row = cell.row
        val column = cell.column
        // checks valid move upwards
        for (i in row..9) {
            val cell = board.cells[i][column]
            if (isNotValidMove(cell)) {
                validMoves.add(board.cells[i-1][column])
                break
            }
        }
        // checks valid move downwards
        for (i in row..0) {
            val cell = board.cells[i][column]
            if (isNotValidMove(cell)) {
                validMoves.add(board.cells[i+1][column])
                break
            }
        }
        // checks valid moves rightwards
        for (i in column..8) {
            val cell = board.cells[row][i]
            if (isNotValidMove(cell)) {
                validMoves.add(board.cells[row][i-1])
                break
            }
        }
        // checks valid moves leftwards
        for (i in column..0) {
            val cell = board.cells[row][i]
            if (isNotValidMove(cell)) {
                validMoves.add(board.cells[row][i+1])
                break
            }
        }
        return validMoves
    }
}

class SuperChip(onLightTeam: Boolean) : Chip(onLightTeam) {
    override fun findValidMove(board: Board, cell: Cell): MutableList<Cell> {
        val validMoves = mutableListOf<Cell>()
        val row = cell.row
        val column = cell.column
        // checks valid move upwards
        for (i in row..9) {
            val cell = board.cells[i][column]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(board.cells[i][column])
        }
        // checks valid move downwards
        for (i in row..0) {
            val cell = board.cells[i][column]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(board.cells[i][column])
        }
        // checks valid moves rightwards
        for (i in column..8) {
            val cell = board.cells[row][i]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(board.cells[row][i])
        }
        // checks valid moves leftwards
        for (i in column..0) {
            val cell = board.cells[row][i]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(board.cells[row][i])
        }
        return validMoves
    }
}