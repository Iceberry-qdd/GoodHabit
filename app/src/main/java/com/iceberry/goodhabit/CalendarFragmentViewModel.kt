package com.iceberry.goodhabit

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.haibin.calendarview.Calendar

class CalendarFragmentViewModel : ViewModel() {
    private val mSchemeDates: MutableMap<String, Calendar> by lazy { mutableMapOf() }

    fun addScheme(calendar: Calendar) {
        mSchemeDates[getSchemeCalendar(
            calendar.year,
            calendar.month,
            calendar.day,
            "签"
        ).toString()] = getSchemeCalendar(calendar.year, calendar.month, calendar.day, "签")
    }

    fun getScheme(): MutableMap<String, Calendar> {
        return mSchemeDates
    }
}

private fun getSchemeCalendar(year: Int, month: Int, day: Int, text: String): Calendar {
    return Calendar().apply {
        this.year = year
        this.month = month
        this.day = day
        scheme = text
        schemeColor = Color.WHITE
    }
}