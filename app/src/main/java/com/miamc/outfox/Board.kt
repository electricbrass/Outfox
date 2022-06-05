package com.miamc.outfox

class Board {

    val rows = 10
    val columns = 9

    var isLightTurn = true

    var selectedCell: Cell? = null

    val cells: Array<Array<Cell>>

    init {
        cells = Array(10) { i -> // rows
            Array(9) { j -> // columns
                if (i < 3 && j > 5) {
                    // make light team goal area
                    GoalCell(null, i, j, true)
                } else if (i > 6 && j < 3) {
                    // make dark team goal area
                    GoalCell(null, i, j, false)
                } else if (i == 4 && j == 4) {
                    // make dark team super chip
                    Cell(SuperChip(false), i, j)
                } else if (i == j) {
                    // place rest of dark team
                    Cell(Chip(false), i, j)
                } else if (i == 5 && j == 4) {
                    // make light team super chip
                    Cell(SuperChip(true), i, j)
                } else if (i == j + 1) {
                    // place rest of light team
                    Cell(Chip(true), i, j)
                } else {
                    // make the rest of the empty cells on the board
                    Cell(null, i, j)
                }
            }
        }
    }

    fun moveChip(row: Int, column: Int): Boolean {
        if (selectedCell == null) {
            return false
        }
        val validMoves = selectedCell?.chip?.findValidMove(this, selectedCell!!) ?: return false
        if (validMoves.contains(cells[row][column])) {
            cells[row][column].chip = selectedCell?.chip
            selectedCell?.chip = null
            selectedCell = null
            if (cells[row][column] is GoalCell) {
                cells[row][column].chip?.inGoal = true
            }
            isLightTurn = !isLightTurn
            return true
        }
        return false
    }

    fun gameWon(): Boolean {
        if (!isLightTurn) {
            for (i in 0..2) {
                for (j in 6..8) {
                    if (!cells[i][j].containsChip()) {
                        return false
                    }
                }
            }
            return true
        } else {
            for (i in 7..9) {
                for (j in 0..2) {
                    if (!cells[i][j].containsChip()) {
                        return false
                    }
                }
            }
            return true
        }
    }

    fun selectCell(row: Int, column: Int): Boolean {
        return if (cells[row][column].chip?.onLightTeam == isLightTurn) {
            selectedCell = cells[row][column]
            true
        } else {
            false
        }
    }
}