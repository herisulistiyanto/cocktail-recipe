package com.andro.indieschool.cocktailapp.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import androidx.palette.graphics.Palette


object PaletteUtils {

    fun getUpperSideDominantColor(bitmap: Bitmap): Int {
        val builder: Palette.Builder = Palette.Builder(bitmap)
            .setRegion(0, 0, bitmap.width, bitmap.height / 2)
        val defaultValue = 0xFFFFFF
        val p = builder.generate()
        return p.getDominantColor(defaultValue)
    }

    fun getLowerSideDominantColor(bitmap: Bitmap): Int {
        val defaultValue = 0xFFFFFF
        val builder: Palette.Builder = Palette.Builder(bitmap)
            .setRegion(0, bitmap.height / 2, bitmap.width, bitmap.height)
        return builder.generate().getDominantColor(defaultValue)
    }

    fun getDominantGradient(bitmap: Bitmap, height: Int, width: Int): Bitmap? {
        val topColor: Int = getUpperSideDominantColor(bitmap)
        val bottomColor: Int = getLowerSideDominantColor(bitmap)

        var topHex = Integer.toHexString(topColor)
        topHex = topHex.trim { it <= ' ' }
        topHex = "#$topHex"

        var bottomHex = Integer.toHexString(bottomColor)
        bottomHex = bottomHex.trim { it <= ' ' }
        bottomHex = "#$bottomHex"
        val colors = intArrayOf(Color.parseColor(topHex), Color.parseColor(bottomHex))
        val mShader: Shader = LinearGradient(
            0f, 0f, (width / 2).toFloat(), (height / 2).toFloat(), colors,
            null, Shader.TileMode.CLAMP
        )
        val m = Matrix().apply { setRotate(90f) }
        mShader.setLocalMatrix(m)
        val paint = Paint().apply {
            shader = mShader
        }
        val resultBitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        //canvas.drawRect(0,0,640,1137,paint);
        val matrix = Matrix()
        canvas.drawBitmap(resultBitmap, matrix, paint)
        return resultBitmap
    }

}