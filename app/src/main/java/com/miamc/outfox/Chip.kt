package com.miamc.outfox

open class Chip(private val onLightTeam: Boolean) {
    private var inGoal = false

    open fun findValidMove(board: Board): MutableList<Cell> {
        return mutableListOf()
    }
}

class SuperChip(onLightTeam: Boolean) : Chip(onLightTeam) {
    override fun findValidMove(board: Board): MutableList<Cell> {
        return mutableListOf()
    }
}