package com.miamc.outfox

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class BoardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val circleRad = 10.0F * (context.resources.displayMetrics.densityDpi / 160).toFloat()
    private val blackCircleRad = 20.0F * (context.resources.displayMetrics.densityDpi / 160).toFloat()
    private val squareSize = (60 * (context.resources.displayMetrics.densityDpi / 160))

    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }


    private val canvas = Canvas().apply {

    }

    private val background = Rect(0, 0, squareSize * 9, squareSize * 10)
    private val darkGoal = Rect(0, squareSize * 7, squareSize * 3, squareSize * 10)
    private val lightGoal = Rect(squareSize * 6, 0, squareSize * 9, squareSize * 3)
    private val grid: Array<Array<Rect>> = Array(10) { i ->
        Array (9) { j ->
            Rect(j * squareSize, i * squareSize,
                (j * squareSize) + squareSize, (i * squareSize) + squareSize)
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
            paint.color = context.getColor(R.color.mid_brown)
            drawRect(background, paint)
            paint.color = context.getColor(R.color.dark_brown)
            drawRect(darkGoal, paint)
            paint.color = context.getColor(R.color.light_brown)
            drawRect(lightGoal, paint)
            for(i in 0..9) {
                for (j in 0..8) {
                    // draw grid
                    paint.style = Paint.Style.STROKE
                    paint.color = context.getColor(R.color.black)
                    drawRect(grid[i][j], paint)
                    // draw super chip circles
                    if (j == 4 && (i == 4 || i == 5)) {
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), blackCircleRad, paint)
                    }
                    // draw dark circles
                    if (i == j) {
                        paint.style = Paint.Style.FILL_AND_STROKE
                        paint.color = context.getColor(R.color.dark_brown)
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), circleRad, paint)
                    }
                    // draw light circles
                    if (i == j + 1) {
                        paint.style = Paint.Style.FILL_AND_STROKE
                        paint.color = context.getColor(R.color.light_brown)
                        drawCircle(squareToCircleCoord(j), squareToCircleCoord(i), circleRad, paint)
                    }
                }
            }
        }
    }
}