package com.miamc.outfox

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val circleRad = 10.0F * (context.resources.displayMetrics.densityDpi / 160).toFloat()
    private val blackCircleRad = 20.0F * (context.resources.displayMetrics.densityDpi / 160).toFloat()
    private val squareSize = (60 * (context.resources.displayMetrics.densityDpi / 160))

    private val fillPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    private val strokePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = context.getColor(R.color.black)

    }


    // private val canvas: Canvas

    private val board = Board().apply {

    }

    private val selectSquare = Rect(0, 0, 0, 0)
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
        selectSquare.let {
            selectSquare.left = column * squareSize
            selectSquare.right = column * squareSize + squareSize
            selectSquare.top = row * squareSize
            selectSquare.bottom = row * squareSize + squareSize
        }
    }

    private fun squareToCircleCoord(coord: Int): Float {
        return ((coord * squareSize) + (squareSize / 2)).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        setMeasuredDimension(squareSize * 9, squareSize * 10)
    }

    override fun onDraw(canvas: Canvas?) {
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
                    // if a cell has been tapped, draw a color over it
                    if (board.cells[i][j].isSelected) {
                        updateSelectSquare(i, j)
                        fillPaint.setARGB(100, 0, 255, 0)
                        drawRect(selectSquare, fillPaint)
                    }
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        val row = (event?.y?.toInt())?.div(squareSize)
        val column = (event?.x?.toInt())?.div(squareSize)
        if (row == null || column == null) {
            return false
        }
        board.cells[row][column].isSelected = true
        invalidate()
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}