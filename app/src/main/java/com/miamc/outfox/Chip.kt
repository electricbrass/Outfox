package com.miamc.outfox

import kotlin.math.min

open class Chip(val onLightTeam: Boolean) {
    var inGoal = false

    /* returns true if cell is not a possible destination for a move, either due to having a chip
    * already or being in the opponents goal area
    * */
    protected fun isNotValidMove(cell: Cell): Boolean {
        return if (inGoal) {
            (cell.containsChip() || cell !is GoalCell)
        } else {
            (cell.containsChip() ||
                    ((cell is GoalCell) && (cell.isLightTeam != this.onLightTeam)))
        }
    }

    open fun findValidMove(board: Board, containingCell: Cell): MutableSet<Cell> {
        val validMoves = mutableSetOf<Cell>()
        val row = containingCell.row
        val column = containingCell.column
        // checks valid move downwards
        for (i in (row + 1)..9) {
            val cell = board.cells[i][column]
            if (isNotValidMove(cell)) {
                validMoves.add(board.cells[i-1][column])
                break
            }
            if (i == 9) {
                validMoves.add(board.cells[9][column])
            }
        }
        // checks valid move upwards
        for (i in (row - 1) downTo 0) {
            val cell = board.cells[i][column]
            if (isNotValidMove(cell)) {
                validMoves.add(board.cells[i+1][column])
                break
            }
            if (i == 0) {
                validMoves.add(board.cells[0][column])
            }
        }
        // checks valid moves rightwards
        for (i in (column + 1)..8) {
            val cell = board.cells[row][i]
            if (isNotValidMove(cell)) {
                validMoves.add(board.cells[row][i-1])
                break
            }
            if (i == 8) {
                validMoves.add(board.cells[row][8])
            }
        }
        // checks valid moves leftwards
        for (i in (column - 1) downTo 0) {
            val cell = board.cells[row][i]
            if (isNotValidMove(cell)) {
                validMoves.add(board.cells[row][i+1])
                break
            }
            if (i == 0) {
                validMoves.add(board.cells[row][0])
            }
        }
        validMoves.remove(containingCell)
        return validMoves
    }
}

class SuperChip(onLightTeam: Boolean) : Chip(onLightTeam) {
    override fun findValidMove(board: Board, containingCell: Cell): MutableSet<Cell> {
        val validMoves = mutableSetOf<Cell>()
        val row = containingCell.row
        val column = containingCell.column
        // TODO change loops to while loops maybe?
        // checks valid move upwards
        for (i in (row + 1)..9) {
            val cell = board.cells[i][column]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(board.cells[i][column])
        }
        // checks valid move downwards
        for (i in (row - 1) downTo 0) {
            val cell = board.cells[i][column]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(board.cells[i][column])
        }
        // checks valid moves rightwards
        for (i in (column + 1)..8) {
            val cell = board.cells[row][i]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(board.cells[row][i])
        }
        // checks valid moves leftwards
        for (i in (column - 1) downTo 0) {
            val cell = board.cells[row][i]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(board.cells[row][i])
        }
        // checks down and right
        for (i in 0..(min(8 - row, 7 - column))) {
                val cell = board.cells[row + 1 + i][column + 1 + i]
                if (isNotValidMove(cell)) {
                    break
                }
                validMoves.add(cell)
        }
        // checks down and left
        for (i in 0..(min(8 - row, column - 1))) {
            val cell = board.cells[row + 1 + i][column - 1 - i]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(cell)
        }
        // checks up and left
        for (i in 0..(min(row - 1, column - 1))) {
            val cell = board.cells[row - 1 - i][column - 1 - i]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(cell)
        }
        // checks up and right
        for (i in 0..(min(row - 1, 7 - column))) {
            val cell = board.cells[row - 1 - i][column + 1 + i]
            if (isNotValidMove(cell)) {
                break
            }
            validMoves.add(cell)
        }
        validMoves.remove(containingCell)
        return validMoves
    }
}