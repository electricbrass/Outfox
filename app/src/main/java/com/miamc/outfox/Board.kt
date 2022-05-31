package com.miamc.outfox

class Board {
    init {
        val cells = Array(10) { i -> // rows
            Array(9) { j -> // columns
                if (i <= 3 && j > 6) {
                    // make light team goal area
                    GoalCell(null, true)
                } else if (i > 7 && j <= 3) {
                    // make dark team goal area
                    GoalCell(null, false)
                } else if (i == 5 && j == 5) {
                    // make light team super chip
                    Cell(SuperChip(true))
                } else if (i == j) {
                    // place rest of light team
                    Cell(Chip(true))
                } else if (i == 5 && j == 6) {
                    // make dark team chip
                    Cell(SuperChip(false))
                } else if (i == j - 1) {
                    // place rest of dark team
                    Cell(Chip(false))
                } else {
                    // make the rest of the empty cells on the board
                    Cell(null)
                }
            }
        }
    }
}