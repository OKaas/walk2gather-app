package com.walk2gather.model.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class CanvasView(internal var context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val RADIUS = 5f

    var canvas: Canvas = Canvas()
    var paint: Paint = Paint()
    var points: ArrayList<Pair<Double, Double>> = arrayListOf()

    init {
        paint.color = Color.BLACK
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // your Canvas will draw onto the defined Bitmap
        var mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // draw the mPath with the mPaint on the canvas when onDraw

        points.forEach{
            canvas.drawCircle(it.first.toFloat(), it.second.toFloat(), RADIUS, paint)
        }

    }

    fun addPoint(x: Double, y: Double){
        points.add(Pair(x, y))
    }
}