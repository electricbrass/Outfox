package com.miamc.outfox

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.max
import kotlin.math.min

class BoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val board = if (context is MainActivity) {
        context.board
    } else {
        Board()
    }

    var gameWon = false
    private val metrics = context.resources.displayMetrics
    // private val squareSize = (60 * (context.resources.displayMetrics.densityDpi / 160))
    private var squareSize = if (metrics.heightPixels < metrics.widthPixels) {
        metrics.heightPixels / board.rows
    } else {
        metrics.widthPixels / board.columns
    }
//    private val circleRad = 10.0F * (metrics.densityDpi / 160).toFloat()
//    private val chipRad = 21.0F * (metrics.densityDpi / 160).toFloat()
//    private val blackCircleRad = 20.0F * (metrics.densityDpi / 160).toFloat()
    private var circleRad = (squareSize / 6).toFloat()
    private var blackCircleRad = circleRad * 2
    private var chipRad = blackCircleRad * 1.05F


    private val fillPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    private val strokePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = context.getColor(R.color.black)

    }


    // private val canvas: Canvas

    // highlights selected cell
    private val selectSquare = Rect(0, 0, 0, 0)
    // highlights valid moves
    private var moveSquares: MutableList<Rect> = mutableListOf()
    private val background = Rect(0, 0, squareSize * 9, squareSize * 10)
    private val darkGoal = Rect(0, squareSize * 7, squareSize * 3, squareSize * 10)
    private val lightGoal = Rect(squareSize * 6, 0, squareSize * 9, squareSize * 3)
    private val grid: Array<Array<Rect>> = Array(10) { i ->
        Array (9) { j ->
            Rect(j * squareSize, i * squareSize,
                (j * squareSize) + squareSize, (i * squareSize) + squareSize)
        }
    }

    private fun updateSelectSquare(row: Int, column: Int) {
        selectSquare.left = column * squareSize
        selectSquare.right = column * squareSize + squareSize
        selectSquare.top = row * squareSize
        selectSquare.bottom = row * squareSize + squareSize
    }

    private fun createMoveSquare(row: Int, column: Int): Rect {
        return Rect(column * squareSize, row * squareSize, column * squareSize + squareSize, row * squareSize + squareSize)
    }

    private fun squareToCircleCoord(coord: Int): Float {
        return ((coord * squareSize) + (squareSize / 2)).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        setMeasuredDimension(squareSize * 9, squareSize * 10)
    }

    override fun onDraw(canvas: Canvas?) {
        squareSize = if (metrics.heightPixels < metrics.widthPixels) {
            metrics.heightPixels / board.rows
        } else {
            metrics.widthPixels / board.columns
        }
        super.onDraw(canvas)
        canvas?.apply {
            fillPaint.color = context.getColor(R.color.mid_brown)
            drawRect(background, fillPaint)
            fillPaint.color = context.getColor(R.color.dark_brown)
            drawRect(darkGoal, fillPaint)
            fillPaint.color = context.getColor(R.color.light_brown)
            drawRect(lightGoal, fillPaint)
            for(i in 0..9) {
                for (j in 0..8) {
                    // draw grid
                    drawRect(grid[i][j], strokePaint)
                    // draw super chip circles
                    if (j == 4 && (i == 4 || i == 5)) {
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), blackCircleRad, strokePaint)
                    }
                    // draw dark circles
                    if (i == j) {
                        fillPaint.color = context.getColor(R.color.dark_brown)
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), circleRad, fillPaint)
                    }
                    // draw light circles
                    if (i == j + 1) {
                        fillPaint.color = context.getColor(R.color.light_brown)
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), circleRad, fillPaint)
                    }
                    // draw chips
                    if (board.cells[i][j].chip?.onLightTeam == true) {
                        fillPaint.color = context.getColor(R.color.light_brown)
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), chipRad, fillPaint)
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), chipRad, strokePaint)
                    }
                    if (board.cells[i][j].chip?.onLightTeam == false) {
                        fillPaint.color = context.getColor(R.color.dark_brown)
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), chipRad, fillPaint)
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), chipRad, strokePaint)
                    }
                    // add dot to super chips
                    if (board.cells[i][j].chip is SuperChip) {
                        fillPaint.color = context.getColor(R.color.black)
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), circleRad, fillPaint)
                    }
                    // if a cell has been tapped, draw a color over it
                    if (board.cells[i][j] === board.selectedCell) {
                        fillPaint.color = context.getColor(R.color.select_square)
                        drawRect(selectSquare, fillPaint)
                    }
                    // draw valid move target
                    fillPaint.color = context.getColor(R.color.move_square)

                    for (square in moveSquares) {
                        drawRect(square, fillPaint)
                    }
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (gameWon) {
            return false
        }
        val row = (event?.y?.toInt())?.div(squareSize)?.let { max(min(it, 9), 0) }
        val column = (event?.x?.toInt())?.div(squareSize)?.let { max(min(it, 8), 0) }
        if (row == null || column == null) {
            return false
        }
        if (board.moveChip(row, column)) {
            performClick()
            moveSquares.clear()
            invalidate()
            return true
        }
        if (board.selectCell(row, column)) {
            updateSelectSquare(row, column)
        } else {
            // temporary make invisible
            updateSelectSquare(0, 0)
            selectSquare.right = 0
            selectSquare.bottom = 0
            board.selectedCell = null
        }
        val validMoves = board.selectedCell?.chip?.findValidMove(board, board.cells[row][column])
        moveSquares.clear()
        if (validMoves != null && validMoves.size > 0) {
            for (move in validMoves) {
                moveSquares.add(createMoveSquare(move.row, move.column))
            }
        } else {
            // remove squares if no valid moves
            moveSquares.clear()
        }
        invalidate()
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}