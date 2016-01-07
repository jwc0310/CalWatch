/*
 * CalWatch
 * Copyright (C) 2014 by Dan Wallach
 * Home page: http://www.cs.rice.edu/~dwallach/calwatch/
 * Licensing: http://www.cs.rice.edu/~dwallach/calwatch/licensing.html
 */
package org.dwallach.calwatch

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.util.SparseArray

/**
 * Cheesy helper for getting Paint values for calendar events and making sure we don't allocate
 * the same color twice.
 * Created by dwallach on 8/15/14.
 */
object PaintCan {
    private val TAG = "PaintCan"
    private var map: SparseArray<Paint>? = null

    fun getCalendarPaint(argb: Int): Paint {
        var retPaint: Paint?
        val argbInt = argb

        if (map == null)
            map = SparseArray<Paint>()

        retPaint = map!!.get(argbInt)
        if (retPaint == null) {
            retPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            retPaint.strokeJoin = Paint.Join.BEVEL
            retPaint.color = argb
            retPaint.style = Paint.Style.FILL

            map!!.put(argbInt, retPaint)

        }
        // Log.v(TAG, "get paint: " + Integer.toHexString(argb));

        return retPaint
    }

    private var palette: Array<Array<Paint?>>? = null

    const val styleNormal = 0
    const val styleAmbient = 1
    const val styleLowBit = 2
    const val styleMax = 2

    private fun newPaint(argb: Int, style: Int, textSize: Float, strokeWidth: Float): Paint {
        val retPaint = Paint(Paint.SUBPIXEL_TEXT_FLAG or Paint.HINTING_ON)
        retPaint.strokeCap = Paint.Cap.SQUARE
        retPaint.strokeWidth = strokeWidth
        retPaint.textSize = textSize
        retPaint.style = Paint.Style.FILL
        retPaint.textAlign = Paint.Align.CENTER

        when (style) {
            styleNormal -> {
                retPaint.isAntiAlias = true
                retPaint.color = argb
            }

            styleLowBit -> {
                retPaint.isAntiAlias = false
                if (argb and 16777215 > 0)
                    retPaint.color = -1 // any non-black color mapped to pure white
                else
                    retPaint.color = -16777216
            }

            styleAmbient -> {
                retPaint.isAntiAlias = true
                retPaint.color = argbToGreyARGB(argb)
            }

            else -> Log.e(TAG, "newPaint: unknown style: " + style)
        }

        return retPaint
    }

    const val colorBatteryLow = 0
    const val colorBatteryCritical = 1
    const val colorSecondHand = 2
    const val colorSecondHandShadow = 3
    const val colorMinuteHand = 4
    const val colorHourHand = 5
    const val colorArcShadow = 6
    const val colorSmallTextAndLines = 7
    const val colorBigTextAndLines = 8
    const val colorBlackFill = 9
    const val colorSmallShadow = 10
    const val colorBigShadow = 11
    const val colorStopwatchStroke = 12
    const val colorStopwatchFill = 13
    const val colorStopwatchSeconds = 14
    const val colorTimerStroke = 15
    const val colorTimerFill = 16
    const val colorMax = 16


    /**
     * Create all the Paint instances used in the watch face. Call this every time the
     * watch radius changes, perhaps because of a resize event on the phone. Make sure
     * to call this *before* trying to get() any colors, or you'll get a NullPointerException.

     * @param radius Radius of the watch face, used to scale all of the line widths
     */
    fun initPaintBucket(radius: Float) {
        Log.v(TAG, "initPaintBucket")

        val textSize = radius / 3f
        val smTextSize = radius / 6f
        val lineWidth = radius / 20f

        // TODO: redo this in a functional or at least immediate way

        palette = Array(styleMax + 1) { arrayOfNulls<Paint>(colorMax + 1) }

        for (style in 0..styleMax) {
            palette!![style][colorBatteryLow] = newPaint(Color.YELLOW, style, smTextSize, lineWidth / 3f)
            palette!![style][colorBatteryCritical] = newPaint(Color.RED, style, smTextSize, lineWidth / 3f)
            palette!![style][colorSecondHand] = newPaint(Color.RED, style, smTextSize, lineWidth / 3f)
            palette!![style][colorSecondHandShadow] = newPaint(Color.BLACK, style, smTextSize, lineWidth / 8f)
            palette!![style][colorMinuteHand] = newPaint(Color.WHITE, style, textSize, lineWidth)
            palette!![style][colorHourHand] = newPaint(Color.WHITE, style, textSize, lineWidth * 1.5f)
            palette!![style][colorArcShadow] = newPaint(Color.BLACK, style, smTextSize, lineWidth / 6f)
            palette!![style][colorSmallTextAndLines] = newPaint(Color.WHITE, style, smTextSize, lineWidth / 3f)
            palette!![style][colorBigTextAndLines] = newPaint(Color.WHITE, style, textSize, lineWidth)
            palette!![style][colorBigShadow] = newPaint(Color.BLACK, style, textSize, lineWidth / 2f)
            palette!![style][colorBlackFill] = newPaint(Color.BLACK, style, textSize, lineWidth)
            palette!![style][colorSmallShadow] = newPaint(Color.BLACK, style, smTextSize, lineWidth / 4f)

            palette!![style][colorStopwatchSeconds] = newPaint(-8346638, style, smTextSize, lineWidth / 8f)  // light blue
            palette!![style][colorStopwatchStroke] = newPaint(-8346638, style, smTextSize, lineWidth / 3f)  // light blue
            palette!![style][colorStopwatchFill] = newPaint(-1870617614, style, smTextSize, lineWidth / 3f)  // light blue + transparency
            palette!![style][colorTimerStroke] = newPaint(-864384, style, smTextSize, lineWidth / 3f) // orange-ish
            palette!![style][colorTimerFill] = newPaint(-1863135360, style, smTextSize, lineWidth / 3f) // orange-ish + transparency

            // shadows are stroke, not fill, so we fix that here
            palette!![style][colorSecondHandShadow]?.style = Paint.Style.STROKE
            palette!![style][colorArcShadow]?.style = Paint.Style.STROKE
            palette!![style][colorSmallShadow]?.style = Paint.Style.STROKE
            palette!![style][colorBigShadow]?.style = Paint.Style.STROKE
            palette!![style][colorStopwatchStroke]?.style = Paint.Style.STROKE
            palette!![style][colorTimerStroke]?.style = Paint.Style.STROKE

            // by default, text is centered, but some styles want it on the left
            // (these are the places where we'll eventually have to do more work for RTL languages)
            palette!![style][colorSmallTextAndLines]?.textAlign = Paint.Align.LEFT
            palette!![style][colorSmallShadow]?.textAlign = Paint.Align.LEFT
        }

        // a couple things that are backwards for low-bit mode: we'll be drawing some "shadows" as
        // white outlines around black centers, "hollowing out" the hands as we're supposed to do
        palette!![styleLowBit][colorSecondHandShadow]?.color = Color.WHITE
        palette!![styleLowBit][colorSecondHandShadow]?.strokeWidth = lineWidth / 6f
        palette!![styleLowBit][colorMinuteHand]?.color = Color.BLACK
        palette!![styleLowBit][colorHourHand]?.color = Color.BLACK
        palette!![styleLowBit][colorArcShadow]?.color = Color.WHITE
        palette!![styleLowBit][colorSmallShadow]?.color = Color.WHITE
        palette!![styleLowBit][colorBigShadow]?.color = Color.WHITE
        palette!![styleLowBit][colorTimerFill]?.color = Color.BLACK
        palette!![styleLowBit][colorStopwatchFill]?.color = Color.BLACK
        palette!![styleLowBit][colorTimerStroke]?.strokeWidth = lineWidth / 6f
        palette!![styleLowBit][colorStopwatchStroke]?.strokeWidth = lineWidth / 6f
    }

    operator fun get(style: Int, colorID: Int): Paint {
        return palette!![style][colorID]!!
    }

    operator fun get(ambientLowBit: Boolean, ambientMode: Boolean, colorID: Int): Paint {
        if (ambientLowBit && ambientMode)
            return get(styleLowBit, colorID)
        if (ambientMode)
            return get(styleAmbient, colorID)
        return get(styleNormal, colorID)
    }

    fun getCalendarGreyPaint(argb: Int): Paint {
        return getCalendarPaint(argbToGreyARGB(argb))
    }

    private fun argbToLuminance(argb: Int): Float {
        val r = argb and 16711680 shr 16
        val g = argb and 65280 shr 8
        val b = argb and 255

        val fr = r / 255.0f
        val fg = g / 255.0f
        val fb = b / 255.0f

        // Many different codes (for reference):
        // http://www.f4.fhtw-berlin.de/~barthel/ImageJ/ColorInspector/HTMLHelp/farbraumJava.htm

        // This code is an approximation of the CIE spec for sRGB -> luminance. The real spec has even
        // more complexity for the gamma conversion. In practice, this seems to yield very dark colors,
        // so we're going with the computationally cheaper and pragmatically prettier YUV conversion.
        // float fy = (float) (.2126f * Math.pow(fr, gamma) + .7152f * Math.pow(fg, gamma) + .0722f * Math.pow(fb, gamma));

        // This line is from the YUV variant, which leaves out the gamma correction and gives less
        // weight to green. Seems to look better in practice even if it's "wrong".
        var fy = .299f * fr + .587f * fg + .114f * fb

        // clipping shouldn't be necessary, but paranoia is warranted
        if (fy > 1.0f) fy = 1.0f

        return fy

    }

    private fun argbToGreyARGB(argb: Int): Int {
        val a = argb and -16777216 shr 24
        val y = (argbToLuminance(argb) * 255f).toInt()
        return a shl 24 or (y shl 16) or (y shl 8) or y
    }
}