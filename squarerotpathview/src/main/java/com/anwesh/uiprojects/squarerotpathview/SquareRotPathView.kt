package com.anwesh.uiprojects.squarerotpathview

/**
 * Created by anweshmishra on 06/09/20.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.content.Context
import android.app.Activity

val colors : Array<Int> = arrayOf(
        "#F44336",
        "#009688",
        "#FF5722",
        "#673AB7",
        "#00BCD4"
).map({Color.parseColor(it)}).toTypedArray()
val parts : Int = 4
val scGap : Float = 0.02f / parts
val strokeFactor : Int = 90
val sizeFactor : Float = 2.9f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")
val rFactor : Float = 5.6f
val deg : Float = 90f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.draweSquareRotPath(scale : Float, w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val sf4 : Float = sf.divideScale(3, parts)
    val size : Float = Math.min(w, h) / sizeFactor
    val r : Float = Math.min(w, h) / rFactor
    val kSize : Float = w / 2 - size
    save()
    translate(w / 2, h / 2)
    for (j in 0..1) {
        save()
        scale(1f - 2 * j, 1f)
        save()
        translate(-kSize, 0f)
        save()
        rotate(90f * sf2)
        drawLine(0f, 0f, 0f, -kSize * sf1, paint)
        restore()
        drawLine(0f, 0f, -size * sf1, 0f, paint)
        restore()
        save()
        translate(-w / 2 + (w / 2 - r) * sf4, 0f)
        drawRect(RectF(0f, -r * sf3, r, 0f), paint)
        restore()
        restore()
    }
    restore()
}


fun Canvas.drawSRPNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    draweSquareRotPath(scale, w, h, paint)
}
