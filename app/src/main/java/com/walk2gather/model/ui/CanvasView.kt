package com.walk2gather.model.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.Bitmap
import android.view.MotionEvent
import com.walk2gather.MapActivity


class CanvasView(internal var context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val RADIUS = 5f

    var canvas: Canvas = Canvas()
    var paint: Paint = Paint()
    var point: Point = Point()
    var mx: Float = 0f
    var my: Float = 0f

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

        canvas.drawCircle(mx, my, RADIUS, paint)
    }

//    fun redraw(){
//        invalidate()
//    }
}