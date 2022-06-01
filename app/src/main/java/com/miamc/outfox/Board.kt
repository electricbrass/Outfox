package com.miamc.outfox

class Board {

    var selectedCell: Cell? = null

    val cells: Array<Array<Cell>>

    init {
        cells = Array(10) { i -> // rows
            Array(9) { j -> // columns
                if (i <= 3 && j > 6) {
                    // make light team goal area
                    GoalCell(null, i, j, true)
                } else if (i > 7 && j <= 3) {
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
}