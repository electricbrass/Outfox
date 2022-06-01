package com.miamc.outfox

open class Chip(private val onLightTeam: Boolean, var row: Int, var column: Int) {
    private var inGoal = false

    open fun findValidMove(board: Board): MutableList<Cell> {
        val validMoves = mutableListOf<Cell>()
        // checks valid move upwards
        for (i in row..9) {
            if (board.cells[i][column].containsChip()) {
                validMoves.add(board.cells[i-1][column])
                break
            }
        }
        // checks valid move downwards
        for (i in row..0) {
            if (board.cells[i][column].containsChip()) {
                validMoves.add(board.cells[i+1][column])
                break
            }
        }
        // checks valid moves rightwards
        for (i in column..8) {
            if (board.cells[row][i].containsChip()) {
                validMoves.add(board.cells[row][i-1])
                break
            }
        }
        // checks valid moves leftwards
        for (i in column..0) {
            if (board.cells[row][i].containsChip()) {
                validMoves.add(board.cells[row][i+1])
                break
            }
        }
        return validMoves
    }
}

class SuperChip(onLightTeam: Boolean, row: Int, column: Int) : Chip(onLightTeam, row, column) {
    override fun findValidMove(board: Board): MutableList<Cell> {
        val validMoves = mutableListOf<Cell>()
        // checks valid move upwards
        for (i in row..9) {
            if (board.cells[i][column].containsChip()) {
                break
            }
            validMoves.add(board.cells[i][column])
        }
        // checks valid move downwards
        for (i in row..0) {
            if (board.cells[i][column].containsChip()) {
                break
            }
            validMoves.add(board.cells[i][column])
        }
        // checks valid moves rightwards
        for (i in column..8) {
            if (board.cells[row][i].containsChip()) {
                break
            }
            validMoves.add(board.cells[row][i])
        }
        // checks valid moves leftwards
        for (i in column..0) {
            if (board.cells[row][i].containsChip()) {
                break
            }
            validMoves.add(board.cells[row][i])
        }
        return validMoves
    }
}