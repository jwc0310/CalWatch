/*
 * CalWatch
 * Copyright (C) 2014 by Dan Wallach
 * Home page: http://www.cs.rice.edu/~dwallach/calwatch/
 * Licensing: http://www.cs.rice.edu/~dwallach/calwatch/licensing.html
 */
package org.dwallach.calwatch

import android.graphics.Path

class EventWrapper(val wireEvent: WireEvent) {
    /**
     * The first time this event is rendered, it will be rendered to a Path, so subsequent calls to
     * render it will go much faster.
     */
    var path: Path? = null
    private val paint = PaintCan.getCalendarPaint(wireEvent.displayColor)
    private val greyPaint = PaintCan.getCalendarGreyPaint(wireEvent.displayColor)
    private val lowBitPaint = PaintCan[PaintCan.STYLE_LOWBIT, PaintCan.COLOR_LOWBIT_CALENDAR_FILL]
    var minLevel: Int = 0
    var maxLevel: Int = 0

    fun getPaint(drawStyle: Int) = when(drawStyle) {
        PaintCan.STYLE_ANTI_BURNIN,PaintCan.STYLE_LOWBIT -> lowBitPaint // for now anyway, we're behaving identically for lowBit and for burnInProtection
        PaintCan.STYLE_AMBIENT -> greyPaint
        else -> paint
    }

    fun overlaps(e: EventWrapper) =
            this.wireEvent.startTime < e.wireEvent.endTime && e.wireEvent.startTime < this.wireEvent.endTime

    override fun toString() =
            "%d -> %d, color(%08x), levels(%d,%d)"
                    .format(wireEvent.startTime, wireEvent.endTime, wireEvent.displayColor, minLevel, maxLevel)
}
