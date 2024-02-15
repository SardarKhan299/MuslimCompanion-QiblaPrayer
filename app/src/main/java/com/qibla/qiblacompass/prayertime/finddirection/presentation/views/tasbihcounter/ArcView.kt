package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class ArcView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private val path = Path()

//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//        canvas?.drawPath(path, paint)
//    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    canvas.drawPath(path, paint)
    }
    fun updatePath(startX: Float, startY: Float, endX: Float, endY: Float) {
        path.reset()
        path.moveTo(startX, startY)
        val controlX = (startX + endX) / 2
        path.quadTo(controlX, startY - 100, endX, endY)
        invalidate()
    }
}
