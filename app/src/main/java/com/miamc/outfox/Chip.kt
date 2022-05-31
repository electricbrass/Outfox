package com.miamc.outfox

open class Chip(val onLightTeam: Boolean) {
    private var inGoal = false

    open fun moveChip() {

    }
}

class SuperChip(onLightTeam: Boolean) : Chip(onLightTeam) {
    override fun moveChip() {

    }
}